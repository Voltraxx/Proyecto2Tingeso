package com.tingeso.msrepairs.clients;

import com.tingeso.msrepairs.configs.FeignClientConfig;
import com.tingeso.msrepairs.models.RepairPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-repair-prices",
        path = "/repair_price",
        configuration = {FeignClientConfig.class})
public interface RepairPriceFeignClient {
    @GetMapping("/type/{type}")
    RepairPrice getRepairPriceByType(@PathVariable String type);
}
