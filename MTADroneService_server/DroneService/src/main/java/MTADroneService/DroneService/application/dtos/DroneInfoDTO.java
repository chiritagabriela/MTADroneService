package MTADroneService.DroneService.application.dtos;

import MTADroneService.DroneService.application.utility.DroneCoordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneInfoDTO {
    String droneID;
    String droneModel;
    String droneStatus;
    DroneCoordinates currentDroneCoordinates;
}
