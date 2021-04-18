package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.models.DroneModel;

import java.util.List;
import java.util.Optional;

public interface DroneService {
    Optional<DroneModel> getDroneInfo(String missionDroneID);
    List<DroneModel> getAllDrones();
}
