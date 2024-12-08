package com.tingeso.msrepairs.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class RepairRequest {
    private ArrayList<String> repairs;
    private Long idCar;
    private String fechaIngreso;
    private String horaIngreso;
    private Integer bonusDiscount;
}
