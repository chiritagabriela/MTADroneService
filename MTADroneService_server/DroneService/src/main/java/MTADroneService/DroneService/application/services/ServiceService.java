package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;

import java.io.IOException;

public interface ServiceService {
    void createMission(MissionInfoDTO missionInfoDTO, String jwtToken, MissionInfoToSend missionInfoToSend) throws IOException;
}
