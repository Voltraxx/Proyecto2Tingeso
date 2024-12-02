package ms3.main.Clients;

import ms3.main.Configurations.FeignClientConfig;
import ms3.main.dtos.EvaluationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ms4",
        path = "/api/v1/evaluation",
        configuration = FeignClientConfig.class
)
public interface EvaluationFeignClient {

    @PostMapping("/evaluate/{rule}")
    Boolean evaluateRule(@RequestBody EvaluationDTO creditEvaluationDTO, @PathVariable String rule);
}
