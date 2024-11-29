package ms3.main.Clients;

import ms3.main.Configurations.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Map;

@FeignClient(value = "ms6",
        path = "/api/v1/totalCost",
        configuration = FeignClientConfig.class
)
public interface TotalCostFeignClient {

    @PostMapping("/calculate")
    Map<String, Object> calculateTotalCost(@RequestBody Map<String, Object> requestBody);
}