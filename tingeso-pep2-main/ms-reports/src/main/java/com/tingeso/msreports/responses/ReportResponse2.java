package com.tingeso.msreports.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportResponse2 {
    private String repairType;
    private String month1Name;
    private String month2Name;
    private String month3Name;
    private Integer month1Count;
    private Integer month2Count;
    private Integer month3Count;
    private Integer month1Monto;
    private Integer month2Monto;
    private Integer month3Monto;
    private Integer month1_2CountVar;
    private Integer month1_2MontoVar;
    private Integer month2_3CountVar;
    private Integer month2_3MontoVar;
}
