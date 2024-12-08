package com.tingeso.mscars.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bonos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String marca;
    private Integer monto;
}