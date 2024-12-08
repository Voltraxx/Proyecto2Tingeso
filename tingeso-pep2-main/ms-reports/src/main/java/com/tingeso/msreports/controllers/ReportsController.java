package com.tingeso.msreports.controllers;

import com.tingeso.msreports.responses.ReportResponse1;
import com.tingeso.msreports.responses.ReportResponse2;
import com.tingeso.msreports.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportsController {
    @Autowired
    ReportsService reportsService;

    @GetMapping("/report1")
    public ResponseEntity<List<ReportResponse1>> getReport1() {
        List<ReportResponse1> report1 = reportsService.report1();
        return ResponseEntity.ok(report1);
    }

    @GetMapping("/report2/{monthYear}")
    public ResponseEntity<List<ReportResponse2>> getReport1(@PathVariable String monthYear) {
        List<ReportResponse2> report2 = reportsService.report2(monthYear);
        return ResponseEntity.ok(report2);
    }
}
