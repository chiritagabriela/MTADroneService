package MTADroneService.DroneService.application.services;


import MTADroneService.DroneService.application.models.MissionModel;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining the MissionService.
 * It is used to implement the business logic behind the mission controller.
 * @author Chirita Gabriela
 */
public interface MissionService {
    List<MissionModel> getMissionDetails(String userID);
    Optional<MissionModel> getMissionByID(String missionID);
    List<MissionModel> getMissionByDroneID(String droneID);
}
