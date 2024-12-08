package com.tingeso.msrepairs.controllers;

import com.tingeso.msrepairs.entities.Repair;
import com.tingeso.msrepairs.requests.RepairRequest;
import com.tingeso.msrepairs.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController {

    @Autowired
    RepairService repairService;

    @GetMapping("/")
    public ResponseEntity<List<Repair>> listRepairs() {
        List<Repair> repairs = repairService.getRepairs();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repair> getRepairById(@PathVariable Long id) {
        Repair repair = repairService.getRepairByID(id);
        return ResponseEntity.ok(repair);
    }

    @GetMapping("/car/{idCar}")
    public ResponseEntity<List<Repair>> getRepairsByCar(@PathVariable Long idCar) {
        List<Repair> repairs = repairService.getRepairsByCar(idCar);
        return ResponseEntity.ok(repairs);
    }

    @PostMapping("/")
    public ResponseEntity<Repair> saveRepair(@RequestBody RepairRequest repairRequest) {
        Repair newRepair = repairService.registerRepair(repairRequest);
        return ResponseEntity.ok(newRepair);
    }

    @PutMapping("/")
    public ResponseEntity<Repair> updateRepair(@RequestBody Repair repair){
        Repair repairUpdated = repairService.updateRepair(repair);
        return ResponseEntity.ok(repairUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRepairById(@PathVariable Long id) throws Exception {
        var isDeleted = repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }
}
