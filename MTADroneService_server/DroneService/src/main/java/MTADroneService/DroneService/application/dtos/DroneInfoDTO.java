package MTADroneService.DroneService.application.dtos;

import MTADroneService.DroneService.application.utility.DroneCoordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class defining the drone DTO.
 *
 * @author Chirita Gabriela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneInfoDTO {
    /**
     * Member description
     */
    String droneID;
    String droneModel;
    String droneStatus;
    DroneCoordinates currentDroneCoordinates;
}
