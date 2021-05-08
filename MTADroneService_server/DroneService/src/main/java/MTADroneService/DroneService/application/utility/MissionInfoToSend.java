package MTADroneService.DroneService.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class defining the mission's information stored in server.
 * @author Chirita Gabriela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoToSend {
    /**
     * Member description
     */
    String missionLatitude;
    String missionLongitude;
    String missionType;
    String videoURL;
    Boolean isTaken;
}
