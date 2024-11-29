package ms3.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SolicitudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolicitudApplication.class, args);
	}

}
