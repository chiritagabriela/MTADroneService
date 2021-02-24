package MTADroneService.DroneService.authentification.dtos;

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
}
