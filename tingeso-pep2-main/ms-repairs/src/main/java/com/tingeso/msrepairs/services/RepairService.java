package com.tingeso.msrepairs.services;

import com.tingeso.msrepairs.clients.CarsFeignClient;
import com.tingeso.msrepairs.clients.RepairPriceFeignClient;
import com.tingeso.msrepairs.entities.Repair;
import com.tingeso.msrepairs.entities.RepairDetail;
import com.tingeso.msrepairs.models.Car;
import com.tingeso.msrepairs.models.RepairPrice;
import com.tingeso.msrepairs.repositories.RepairDetailRepository;
import com.tingeso.msrepairs.repositories.RepairRepository;
import com.tingeso.msrepairs.requests.RepairRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;
    @Autowired
    RepairDetailRepository repairDetailRepository;
    @Autowired
    CarsFeignClient carsFeignClient;
    @Autowired
    RepairPriceFeignClient repairPriceFeignClient;

    public ArrayList<Repair> getRepairs(){
        return (ArrayList<Repair>) repairRepository.findAll();
    }

    public Repair saveRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    public Repair getRepairByID(Long id) {
        return repairRepository.findById(id).orElse(null);
    }

    public List<Repair> getRepairsByCar(Long idCar) {
        return repairRepository.findByIdCar(idCar);
    }

    public Repair updateRepair(Repair repair) {
        if (repair.getFechaSalida() != null) {
            calcRepairTime(repair);
            if (repair.getFechaDespacho() != null) {
                applyDelayCharge(repair);
                applyIvaCharge(repair);
                repair.setStatus("Finalizado");
            }
        }
        return repairRepository.save(repair);
    }

    public boolean deleteRepair(Long id) throws Exception {
        try{
            repairRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // REGISTRAR UNA REPARACION
    public Repair registerRepair(RepairRequest repairRequest) {
        Repair repair = new Repair(repairRequest.getIdCar(),repairRequest.getFechaIngreso(),repairRequest.getHoraIngreso(),repairRequest.getBonusDiscount());
        saveRepair(repair);
        // APLICAR MONTO DE REPARACION INICIAL
        applyRepairPrice(repair,repairRequest.getRepairs());
        // APLICAR CARGOS
        applyKmCharge(repair);
        applyAgeCharge(repair);
        // APLICAR DESCUENTOS
        applyRepairsDiscount(repair);
        applyDayDiscount(repair);
        repair.setMontoTotal(repair.getMontoTotal()-repair.getBonusDiscount());
        repair.setStatus("Pendiente");
        repair.setDelayCharge(0);
        repair.setIvaCharge(0);
        repair.setRepairDays(0);
        return saveRepair(repair);
    }

    public void applyRepairPrice(Repair repair,ArrayList<String> repairs) {
        Car car = carsFeignClient.getCarById(repair.getIdCar());
        String motor = car.getMotor();
        Integer totalPrice = 0;
        RepairPrice repairPrice;
        for (String repairType : repairs) {
            repairPrice = repairPriceFeignClient.getRepairPriceByType(repairType);
            if (motor.equals("Gasolina")) {
                totalPrice+= repairPrice.getMontoG();
                // REGISTRAR REPAIR DETAIL
                RepairDetail repairDetail = new RepairDetail(repair.getId(),repairType,repairPrice.getMontoG(),car.getPatente(),car.getType(),repair.getFechaIngreso(),repair.getHoraIngreso());
                repairDetailRepository.save(repairDetail);
            } else if (motor.equals("Diésel")) {
                totalPrice+= repairPrice.getMontoD();
                // REGISTRAR REPAIR DETAIL
                RepairDetail repairDetail = new RepairDetail(repair.getId(),repairType,repairPrice.getMontoD(),car.getPatente(),car.getType(),repair.getFechaIngreso(),repair.getHoraIngreso());
                repairDetailRepository.save(repairDetail);
            } else if (motor.equals("Híbrido")) {
                totalPrice+= repairPrice.getMontoH();
                // REGISTRAR REPAIR DETAIL
                RepairDetail repairDetail = new RepairDetail(repair.getId(),repairType,repairPrice.getMontoH(),car.getPatente(),car.getType(),repair.getFechaIngreso(),repair.getHoraIngreso());
                repairDetailRepository.save(repairDetail);
            } else {
                totalPrice+= repairPrice.getMontoE();
                // REGISTRAR REPAIR DETAIL
                RepairDetail repairDetail = new RepairDetail(repair.getId(),repairType,repairPrice.getMontoE(),car.getPatente(),car.getType(),repair.getFechaIngreso(),repair.getHoraIngreso());
                repairDetailRepository.save(repairDetail);
            }
        }
        repair.setMontoTotal(totalPrice);
    }

    public void applyKmCharge(Repair repair) {
        Car car = carsFeignClient.getCarById(repair.getIdCar());
        if (car.getKms() >= 0 & car.getKms() <= 5000) {
            repair.setKmCharge(0);
        } else if (car.getKms() >= 5001 & car.getKms() <= 12000) {
            if (car.getType().equals("Sedan") || car.getType().equals("Hatchback")) {
                repair.setKmCharge((int) (repair.getMontoTotal()*0.03));
            } else {
                repair.setKmCharge((int) (repair.getMontoTotal()*0.05));
            }
        } else if (car.getKms() >= 12001 & car.getKms() <= 25000) {
            if (car.getType().equals("Sedan") || car.getType().equals("Hatchback")) {
                repair.setKmCharge((int) (repair.getMontoTotal()*0.07));
            } else {
                repair.setKmCharge((int) (repair.getMontoTotal()*0.09));
            }
        } else if (car.getKms() >= 25001 & car.getKms() <= 40000) {
            repair.setKmCharge((int) (repair.getMontoTotal()*0.12));
        } else {
            repair.setKmCharge((int) (repair.getMontoTotal()*0.2));
        }
        repair.setMontoTotal(repair.getMontoTotal() + repair.getKmCharge());
    }

    public void applyAgeCharge(Repair repair) {
        Car car = carsFeignClient.getCarById(repair.getIdCar());
        int age = java.time.Year.now().getValue() - car.getYear();
        if (age <= 5) {
            repair.setAgeCharge(0);
        } else if (age <= 10) {
            if (car.getType().equals("Sedan") || car.getType().equals("Hatchback")) {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.05));
            } else {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.07));
            }
        } else if (age <= 15) {
            if (car.getType().equals("Sedan") || car.getType().equals("Hatchback")) {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.09));
            } else {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.11));
            }
        } else {
            if (car.getType().equals("Sedan") || car.getType().equals("Hatchback")) {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.15));
            } else {
                repair.setAgeCharge((int) (repair.getMontoTotal()*0.2));
            }
        }
        repair.setMontoTotal(repair.getMontoTotal() + repair.getAgeCharge());
    }

    public void applyDelayCharge(Repair repair) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaSalida = LocalDate.parse(repair.getFechaSalida(), formatter);
        LocalDate fechaDespacho = LocalDate.parse(repair.getFechaDespacho(), formatter);
        Integer diasRetraso = (int) ChronoUnit.DAYS.between(fechaSalida,fechaDespacho);
        Integer recargo = (int) (repair.getMontoTotal()*diasRetraso*0.05);
        repair.setDelayCharge(recargo);
        repair.setMontoTotal(repair.getMontoTotal()+recargo);
    }

    public void applyRepairsDiscount(Repair repair) {
        Car car = carsFeignClient.getCarById(repair.getIdCar());
        // CONTAR REPARACIONES DEL AUTO HECHAS EN LOS ULTIMOS 12 MESES
        List<Repair> repairs = getRepairsByCar(repair.getIdCar());
        if (!repairs.isEmpty()) {
            // CONTAR REPARACIONES QUE NO SUPERAN LOS 12 MESES DESDE LA FECHA ACTUAL
            int count = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaActual = LocalDate.now();
            String fechaX;
            for (int i = 0;i < repairs.size();i++) {
                fechaX = repairs.get(i).getFechaIngreso();
                LocalDate fechaXX = LocalDate.parse(fechaX, formatter);
                if (Math.abs(fechaActual.until(fechaXX).toTotalMonths()) <= 12) {
                    count++;
                }
            }
            if (count == 0) {
                repair.setRepairDiscount(0);
            } else if (count <= 2) {
                if (car.getMotor().equals("Gasolina")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.05));
                } else if (car.getMotor().equals("Diésel")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.07));
                } else if (car.getMotor().equals("Híbrido")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.1));
                } else {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.08));
                }
            } else if (count <= 5) {
                if (car.getMotor().equals("Gasolina")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.1));
                } else if (car.getMotor().equals("Diésel")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.12));
                } else if (car.getMotor().equals("Híbrido")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.15));
                } else {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.13));
                }
            } else if (count <= 9) {
                if (car.getMotor().equals("Gasolina")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.15));
                } else if (car.getMotor().equals("Diésel")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.17));
                } else if (car.getMotor().equals("Híbrido")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.2));
                } else {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.18));
                }
            } else {
                if (car.getMotor().equals("Gasolina")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.2));
                } else if (car.getMotor().equals("Diésel")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.22));
                } else if (car.getMotor().equals("Híbrido")) {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.25));
                } else {
                    repair.setRepairDiscount((int)(repair.getMontoTotal()*0.23));
                }
            }
            repair.setMontoTotal(repair.getMontoTotal()-repair.getRepairDiscount());
        } else {
            repair.setRepairDiscount(0);
        }
    }

    public void applyDayDiscount(Repair repair) {
        int descuento = 0;
        // Formateador para parsear la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Convertir la cadena en un objeto LocalDate
        LocalDate fecha = LocalDate.parse(repair.getFechaIngreso(), formatter);
        // Obtener el nombre del día de la semana
        DayOfWeek diaSemana = fecha.getDayOfWeek();
        String nombreDia = diaSemana.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault());

        if (nombreDia.equals("lunes") || nombreDia.equals("jueves")) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
            // Convertir la cadena en un objeto LocalTime
            LocalTime hora = LocalTime.parse(repair.getHoraIngreso(), formatter2);
            // Definir las horas de inicio y fin
            LocalTime horaInicio = LocalTime.parse("09:00", formatter2);
            LocalTime horaFin = LocalTime.parse("12:00", formatter2);
            if (hora.isAfter(horaInicio) && hora.isBefore(horaFin)) {
                descuento = (int) (repair.getMontoTotal()*0.1);
            }
        }
        repair.setDayDiscount(descuento);
        repair.setMontoTotal(repair.getMontoTotal()-descuento);
    }

    public void applyIvaCharge(Repair repair) {
        repair.setIvaCharge((int) (repair.getMontoTotal()*0.19));
        repair.setMontoTotal(repair.getMontoTotal() + repair.getIvaCharge());
    }

    public void calcRepairTime(Repair repair) {
        if (repair.getFechaSalida() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaIngreso = LocalDate.parse(repair.getFechaIngreso(), formatter);
            LocalDate fechaSalida = LocalDate.parse(repair.getFechaSalida(), formatter);
            Integer diferenciaDias = (int) ChronoUnit.DAYS.between(fechaIngreso.atStartOfDay(), fechaSalida.atStartOfDay());
            repair.setRepairDays(diferenciaDias);
        }
    }
}
