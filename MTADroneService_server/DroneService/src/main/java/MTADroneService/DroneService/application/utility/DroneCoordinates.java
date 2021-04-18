package MTADroneService.DroneService.application.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneCoordinates {
    String currentLatitude;
    String currentLongitude;
    String missionStatus = Utils.MissionStatus.PREPARING.toString();
    List<String> images = new ArrayList<>();
}
