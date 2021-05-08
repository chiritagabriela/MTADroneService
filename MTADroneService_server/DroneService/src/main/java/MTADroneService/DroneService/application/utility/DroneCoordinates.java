package MTADroneService.DroneService.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining the drone's information stored in server.
 * @author Chirita Gabriela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneCoordinates {

    /**
     * Member description
     */
    String currentLatitude;
    String currentLongitude;
    String missionStatus = Utils.MissionStatus.PREPARING.toString();
    List<String> images = new ArrayList<>();
}
