package com.tingeso.msrepairprices.controllers;

import com.tingeso.msrepairprices.entities.RepairPrice;
import com.tingeso.msrepairprices.services.RepairPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair_price")
public class RepairPriceController {
    @Autowired
    RepairPriceService repairPriceService;

    @GetMapping("/")
    public ResponseEntity<List<RepairPrice>> listRepairPrices() {
        List<RepairPrice> repairPrices = repairPriceService.getRepairPrices();
        return ResponseEntity.ok(repairPrices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairPrice> getRepairPriceById(@PathVariable Long id) {
        RepairPrice repairPrice = repairPriceService.getRepairPriceByID(id);
        return ResponseEntity.ok(repairPrice);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<RepairPrice> getRepairPriceByType(@PathVariable String type) {
        RepairPrice repairPrice = repairPriceService.getRepairPriceByType(type);
        return ResponseEntity.ok(repairPrice);
    }

    @PostMapping("/")
    public ResponseEntity<RepairPrice> saveRepairPrice(@RequestBody RepairPrice repairPrice) {
        RepairPrice newRepairPrice = repairPriceService.saveRepairPrice(repairPrice);
        return ResponseEntity.ok(newRepairPrice);
    }

    @PutMapping("/")
    public ResponseEntity<RepairPrice> updateRepairPrice(@RequestBody RepairPrice repairPrice){
        RepairPrice repairPriceUpdated = repairPriceService.updateRepairPrice(repairPrice);
        return ResponseEntity.ok(repairPriceUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRepairPriceById(@PathVariable Long id) throws Exception {
        var isDeleted = repairPriceService.deleteRepairPrice(id);
        return ResponseEntity.noContent().build();
    }
}
