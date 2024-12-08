package com.tingeso.msrepairs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repairs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long idCar;
    private String fechaIngreso;
    private String horaIngreso;
    private Integer montoTotal;
    private String fechaSalida;
    private String horaSalida;
    private String fechaDespacho;
    private String horaDespacho;
    private Integer repairDiscount;
    private Integer dayDiscount;
    private Integer bonusDiscount;
    private Integer kmCharge;
    private Integer ageCharge;
    private Integer delayCharge;
    private Integer ivaCharge;
    private Integer repairDays;
    private String status;

    public Repair(Long idCar, String fechaIngreso, String horaIngreso, Integer bonusDiscount) {
        this.idCar = idCar;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.bonusDiscount = bonusDiscount;
    }
}
