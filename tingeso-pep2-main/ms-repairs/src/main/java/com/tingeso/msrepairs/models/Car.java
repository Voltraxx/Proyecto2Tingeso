package com.tingeso.msrepairs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String patente;
    private String marca;
    private String modelo;
    private String type;
    private int year;
    private String motor;
    private int seats;
    private Float kms;
}
