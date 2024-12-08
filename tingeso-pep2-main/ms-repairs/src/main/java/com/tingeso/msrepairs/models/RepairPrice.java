package com.tingeso.msrepairs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairPrice {
    private String type;
    private int montoG;
    private int montoD;
    private int montoH;
    private int montoE;
}
