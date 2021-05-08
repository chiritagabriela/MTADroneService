package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;

import java.io.IOException;

/**
 * Interface defining the ServiceService.
 * It is used to implement the business logic behind the service controller.
 * @author Chirita Gabriela
 */
public interface ServiceService {
    void createMission(MissionInfoDTO missionInfoDTO, String jwtToken, MissionInfoToSend missionInfoToSend) throws IOException;
}
