package com.tingeso.msrepairs.controllers;

import com.tingeso.msrepairs.entities.RepairDetail;
import com.tingeso.msrepairs.services.RepairDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair_detail")
public class RepairDetailController {
    @Autowired
    RepairDetailService repairDetailService;

    @GetMapping("/")
    public ResponseEntity<List<RepairDetail>> listRepairDetails() {
        List<RepairDetail> repairDetails = repairDetailService.getRepairDetails();
        return ResponseEntity.ok(repairDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairDetail> getRepairDetailById(@PathVariable Long id) {
        RepairDetail repairDetail = repairDetailService.getRepairDetailByID(id);
        return ResponseEntity.ok(repairDetail);
    }

    @GetMapping("/id_repair/{id_repair}")
    public ResponseEntity<List<RepairDetail>> listRepairDetailsByIdRepair(@PathVariable Long id_repair) {
        List<RepairDetail> repairDetails = repairDetailService.getRepairDetailsByIdRepair(id_repair);
        return ResponseEntity.ok(repairDetails);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RepairDetail>> listRepairDetailsByType(@PathVariable String type) {
        List<RepairDetail> repairDetails = repairDetailService.getRepairDetailsByType(type);
        return ResponseEntity.ok(repairDetails);
    }

    @GetMapping("/type_car-type/{type}/{carType}")
    public ResponseEntity<List<RepairDetail>> listRepairDetailsByTypeAndCarType(@PathVariable String type,@PathVariable String carType) {
        List<RepairDetail> repairDetails = repairDetailService.getRepairDetailsByTypeAndCarType(type,carType);
        return ResponseEntity.ok(repairDetails);
    }

    @GetMapping("/type_month-year/{type}/{monthYear}")
    public ResponseEntity<List<RepairDetail>> listRepairDetailsByTypeMonthAndYear(@PathVariable String type,@PathVariable String monthYear) {
        monthYear = monthYear.replace("-","/");
        List<RepairDetail> repairDetails = repairDetailService.getRepairDetailsByTypeMonthAndYear(type,monthYear);
        return ResponseEntity.ok(repairDetails);
    }

    @PostMapping("/")
    public ResponseEntity<RepairDetail> saveRepairDetail(@RequestBody RepairDetail repairDetail) {
        RepairDetail newRepairDetail = repairDetailService.saveRepairDetail(repairDetail);
        return ResponseEntity.ok(newRepairDetail);
    }

    @PutMapping("/")
    public ResponseEntity<RepairDetail> updateRepairDetail(@RequestBody RepairDetail repairDetail){
        RepairDetail repairDetailUpdated = repairDetailService.updateRepairDetail(repairDetail);
        return ResponseEntity.ok(repairDetailUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRepairDetailById(@PathVariable Long id) throws Exception {
        var isDeleted = repairDetailService.deleteRepairDetail(id);
        return ResponseEntity.noContent().build();
    }
}

