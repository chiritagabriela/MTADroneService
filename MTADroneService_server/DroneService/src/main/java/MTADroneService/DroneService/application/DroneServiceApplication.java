package MTADroneService.DroneService.application;
import MTADroneService.DroneService.application.utility.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableMongoAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DroneServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}

}
