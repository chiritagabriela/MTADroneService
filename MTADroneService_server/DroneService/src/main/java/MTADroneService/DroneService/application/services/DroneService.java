package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.models.DroneModel;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining the DroneService.
 * It is used to implement the business logic behind the drone controller.
 * @author Chirita Gabriela
 */
public interface DroneService {
    Optional<DroneModel> getDroneInfo(String missionDroneID);
    List<DroneModel> getAllDrones();
}
