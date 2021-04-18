package MTADroneService.DroneService.application.services;


import MTADroneService.DroneService.application.models.MissionModel;

import java.util.List;
import java.util.Optional;

public interface MissionService {
    List<MissionModel> getMissionDetails(String userID);
    Optional<MissionModel> getMissionByID(String missionID);
    List<MissionModel> getMissionByDroneID(String droneID);
}
