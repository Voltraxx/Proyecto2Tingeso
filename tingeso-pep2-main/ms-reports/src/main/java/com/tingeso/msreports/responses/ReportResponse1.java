package com.tingeso.msreports.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportResponse1 {
    private String repairType;
    private Integer sedanCount;
    private Integer hatchbackCount;
    private Integer suvCount;
    private Integer pickupCount;
    private Integer furgoCount;
    private Integer sedanMonto;
    private Integer hatchbackMonto;
    private Integer suvMonto;
    private Integer pickupMonto;
    private Integer furgoMonto;
}
