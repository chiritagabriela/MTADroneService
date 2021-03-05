package MTADroneService.DroneService.authentification.services;

import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServiceService {
    void createMission(MissionInfoDTO missionInfoDTO) throws IOException;
}
