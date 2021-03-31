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
    String missionDroneID;
    String missionID;
    String missionType;
    String missionLongitudeStart;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    String missionLatitudeStart;
    Date missionDate;
    String missionStatus;
}
