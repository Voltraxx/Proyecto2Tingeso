package com.tingeso.msreports.clients;


import com.tingeso.msreports.configs.FeignClientConfig;
import com.tingeso.msreports.models.RepairDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ms-repairs",
        path = "/repair_detail",
        configuration = {FeignClientConfig.class})
public interface RepairDetailFeignClient {
    @GetMapping("/type_car-type/{type}/{carType}")
    List<RepairDetail> listRepairDetailsByTypeAndCarType(@PathVariable String type, @PathVariable String carType);

    @GetMapping("/type_month-year/{type}/{monthYear}")
    List<RepairDetail> listRepairDetailsByTypeMonthAndYear(@PathVariable String type, @PathVariable String monthYear);
}
