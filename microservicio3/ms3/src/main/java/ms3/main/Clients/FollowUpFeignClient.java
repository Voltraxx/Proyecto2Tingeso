package ms3.main.Clients;

import ms3.main.Configurations.FeignClientConfig;
import ms3.main.dtos.CreditRequestStatusUpdateDTO;
import ms3.main.dtos.StatusUpdateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "ms5",
        path = "/api/v1/followup",
        configuration = FeignClientConfig.class
)
public interface FollowUpFeignClient {

    @PostMapping("/{id}/status")
    StatusUpdateResponseDTO updateCreditRequestStatus(@PathVariable Long id, @RequestBody CreditRequestStatusUpdateDTO payload);
}
