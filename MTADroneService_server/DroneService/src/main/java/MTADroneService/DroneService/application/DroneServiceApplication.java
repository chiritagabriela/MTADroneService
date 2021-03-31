package MTADroneService.DroneService.authentification;

import MTADroneService.DroneService.authentification.image_processing.DetectHumans;
import MTADroneService.DroneService.authentification.image_processing.implementation.DetectHumansImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableMongoAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DroneServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);

		DetectHumans detectHumans = new DetectHumansImpl();
		detectHumans.run();
	}

}
