package ms3.main.Clients;

import ms3.main.Configurations.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ms3.main.dtos.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms2",
        path = "/api/v1/users",
        configuration = FeignClientConfig.class
)
public interface UserFeignClient {

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
