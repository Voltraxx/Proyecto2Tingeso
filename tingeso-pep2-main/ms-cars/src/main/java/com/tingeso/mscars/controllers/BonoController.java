package com.tingeso.mscars.controllers;

import com.tingeso.mscars.entities.Bono;
import com.tingeso.mscars.services.BonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bono")
public class BonoController {
    @Autowired
    BonoService bonoService;

    @GetMapping("/")
    public ResponseEntity<List<Bono>> listBonos() {
        List<Bono> bonos = bonoService.getBonos();
        return ResponseEntity.ok(bonos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bono> getBonoById(@PathVariable Long id) {
        Bono bono = bonoService.getBonoByID(id);
        return ResponseEntity.ok(bono);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Bono>> listBonosByMarca(@PathVariable String marca) {
        List<Bono> bonos = bonoService.getBonosByMarca(marca);
        return ResponseEntity.ok(bonos);
    }

    @PostMapping("/")
    public ResponseEntity<Bono> saveBono(@RequestBody Bono bono) {
        Bono newBono = bonoService.saveBono(bono);
        return ResponseEntity.ok(newBono);
    }

    @PutMapping("/")
    public ResponseEntity<Bono> updateBono(@RequestBody Bono bono){
        Bono bonoUpdated = bonoService.updateBono(bono);
        return ResponseEntity.ok(bonoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBonoById(@PathVariable Long id) throws Exception {
        var isDeleted = bonoService.deleteBono(id);
        return ResponseEntity.noContent().build();
    }
}