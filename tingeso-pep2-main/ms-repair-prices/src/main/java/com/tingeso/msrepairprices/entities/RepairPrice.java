package com.tingeso.msrepairprices.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "repair-prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String type;
    private int montoG;
    private int montoD;
    private int montoH;
    private int montoE;
}
