package com.tingeso.msrepairs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repair-details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private Long idRepair;
    private String type;
    private Integer monto;
    private String patente;
    private String carType;
    private String fechaIngreso;
    private String horaIngreso;

    public RepairDetail(Long idRepair,String type,Integer monto,String patente,String carType,String fechaIngreso,String horaIngreso) {
        this.idRepair = idRepair;
        this.type = type;
        this.monto = monto;
        this.patente = patente;
        this.carType = carType;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
    }
}
