package com.tingeso.msreports.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairDetail {
    private Long idRepair;
    private String type;
    private Integer monto;
    private String patente;
    private String carType;
    private String fechaIngreso;
    private String horaIngreso;
}
