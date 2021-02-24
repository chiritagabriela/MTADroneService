package MTADroneService.DroneService.authentification.dtos;

import MTADroneService.DroneService.authentification.services.RetrieveInfoDrone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoDTO {
    String droneID;
    String missionType;
    String missionLongitude;
    String missionLatitude;
    Date missionDate;
    String missionStatus;
}
