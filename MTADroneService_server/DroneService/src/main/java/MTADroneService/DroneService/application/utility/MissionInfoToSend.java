package MTADroneService.DroneService.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoToSend {
    String missionLatitude;
    String missionLongitude;
    String missionType;
    String videoURL;
    Boolean isTaken;
}
