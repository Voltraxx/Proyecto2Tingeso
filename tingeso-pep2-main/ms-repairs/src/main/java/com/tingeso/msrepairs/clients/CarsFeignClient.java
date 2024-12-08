package com.tingeso.msrepairs.clients;

import com.tingeso.msrepairs.configs.FeignClientConfig;
import com.tingeso.msrepairs.models.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-cars",
            path = "/car",
            configuration = {FeignClientConfig.class})
public interface CarsFeignClient {
    @GetMapping("/{id}")
    Car getCarById(@PathVariable Long id);
}
