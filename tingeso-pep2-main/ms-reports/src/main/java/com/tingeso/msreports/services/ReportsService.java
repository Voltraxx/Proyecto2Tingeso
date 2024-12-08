package com.tingeso.msreports.services;

import com.tingeso.msreports.clients.RepairDetailFeignClient;
import com.tingeso.msreports.models.RepairDetail;
import com.tingeso.msreports.responses.ReportResponse1;
import com.tingeso.msreports.responses.ReportResponse2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReportsService {
    @Autowired
    RepairDetailFeignClient repairDetailFeignClient;

    public List<ReportResponse1> report1() {
        List<ReportResponse1> report1 = new ArrayList<>();
        String[] repairTypes = {
                "Reparaciones del Sistema de Frenos",
                "Servicio del Sistema de Refrigeración",
                "Reparaciones del Motor",
                "Reparaciones de la Transmisión",
                "Reparación del Sistema Eléctrico",
                "Reparaciones del Sistema de Escape",
                "Reparación de Neumáticos y Ruedas",
                "Reparaciones de la Suspensión y la Dirección",
                "Reparación del Sistema de Aire Acondicionado y Calefacción",
                "Reparaciones del Sistema de Combustible",
                "Reparación y Reemplazo del Parabrisas y Cristales"
        };
        String[] carTypes = {
                "Sedan",
                "Hatchback",
                "SUV",
                "Pickup",
                "Furgoneta"
        };

        for (String repairType: repairTypes) {
            ReportResponse1 reportResponse = new ReportResponse1();
            reportResponse.setRepairType(repairType);
            for (String carType: carTypes) {
                List<RepairDetail> repairDetails = repairDetailFeignClient.listRepairDetailsByTypeAndCarType(repairType,carType);
                Integer monto = 0;
                for (RepairDetail repairDetail : repairDetails) {
                    monto += repairDetail.getMonto();
                }
                if (carType.equals("Sedan")) {
                    reportResponse.setSedanCount(repairDetails.size());
                    reportResponse.setSedanMonto(monto);
                } else if (carType.equals("Hatchback")) {
                    reportResponse.setHatchbackCount(repairDetails.size());
                    reportResponse.setHatchbackMonto(monto);
                } else if (carType.equals("SUV")) {
                    reportResponse.setSuvCount(repairDetails.size());
                    reportResponse.setSuvMonto(monto);
                } else if (carType.equals("Pickup")) {
                    reportResponse.setPickupCount(repairDetails.size());
                    reportResponse.setPickupMonto(monto);
                } else {
                    reportResponse.setFurgoCount(repairDetails.size());
                    reportResponse.setFurgoMonto(monto);
                }
            }
            report1.add(reportResponse);
        }
        return report1;
    }

    public List<ReportResponse2> report2(String monthYear) {
        List<ReportResponse2> report2 = new ArrayList<>();
        String[] repairTypes = {
                "Reparaciones del Sistema de Frenos",
                "Servicio del Sistema de Refrigeración",
                "Reparaciones del Motor",
                "Reparaciones de la Transmisión",
                "Reparación del Sistema Eléctrico",
                "Reparaciones del Sistema de Escape",
                "Reparación de Neumáticos y Ruedas",
                "Reparaciones de la Suspensión y la Dirección",
                "Reparación del Sistema de Aire Acondicionado y Calefacción",
                "Reparaciones del Sistema de Combustible",
                "Reparación y Reemplazo del Parabrisas y Cristales"
        };

        // Define el formateador para el formato "MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");

        // Parsear la fecha de entrada
        LocalDate date = LocalDate.parse("01-" + monthYear, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date1 = date.minusMonths(1);
        LocalDate date2 = date.minusMonths(2);

        // Obtener el nombre del mes en español
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));
        String monthName1 = date.format(monthFormatter);
        String monthName2 = date1.format(monthFormatter);
        String monthName3 = date2.format(monthFormatter);

        // Capitalizar la primera letra del mes
        monthName1 = monthName1.substring(0, 1).toUpperCase() + monthName1.substring(1);
        monthName2 = monthName2.substring(0, 1).toUpperCase() + monthName2.substring(1);
        monthName3 = monthName3.substring(0, 1).toUpperCase() + monthName3.substring(1);

        // Obtener las dos fechas anteriores
        String previousMonth1 = date1.format(formatter);
        String previousMonth2 = date2.format(formatter);

        for (String repairType: repairTypes) {
            ReportResponse2 reportResponse = new ReportResponse2();
            reportResponse.setRepairType(repairType);
            List<RepairDetail> rdMonth1 = repairDetailFeignClient.listRepairDetailsByTypeMonthAndYear(repairType,monthYear);
            List<RepairDetail> rdMonth2 = repairDetailFeignClient.listRepairDetailsByTypeMonthAndYear(repairType,previousMonth1);
            List<RepairDetail> rdMonth3 = repairDetailFeignClient.listRepairDetailsByTypeMonthAndYear(repairType,previousMonth2);
            Integer monto1 = 0,monto2 = 0,monto3 = 0;
            for (RepairDetail rd : rdMonth1) {
                monto1 += rd.getMonto();
            }
            for (RepairDetail rd : rdMonth2) {
                monto2 += rd.getMonto();
            }
            for (RepairDetail rd : rdMonth3) {
                monto3 += rd.getMonto();
            }
            reportResponse.setMonth1Name(monthName1);
            reportResponse.setMonth2Name(monthName2);
            reportResponse.setMonth3Name(monthName3);
            reportResponse.setMonth1Count(rdMonth1.size());
            reportResponse.setMonth2Count(rdMonth2.size());
            reportResponse.setMonth3Count(rdMonth3.size());
            reportResponse.setMonth1Monto(monto1);
            reportResponse.setMonth2Monto(monto2);
            reportResponse.setMonth3Monto(monto3);

            float var1_12 = 0,var1_23 = 0,var2_12 = 0,var2_23 = 0;

            if (!rdMonth2.isEmpty()) {
                var1_12 = ((float) (rdMonth1.size() - rdMonth2.size()) / rdMonth2.size()) * 100;
            }
            if (!rdMonth3.isEmpty()) {
                var1_23 = ((float) (rdMonth2.size() - rdMonth3.size()) / rdMonth3.size()) * 100;
            }
            if (monto2 != 0) {
                var2_12 = ((float) (monto1 - monto2) / monto2) * 100;
            }
            if (monto3 != 0) {
                var2_23 = ((float) (monto2 - monto3) / monto3) * 100;
            }

            reportResponse.setMonth1_2CountVar((int)var1_12);
            reportResponse.setMonth2_3CountVar((int)var1_23);
            reportResponse.setMonth1_2MontoVar((int)var2_12);
            reportResponse.setMonth2_3MontoVar((int)var2_23);

            report2.add(reportResponse);
        }
        return report2;
    }
}
