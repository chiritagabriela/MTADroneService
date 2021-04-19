package MTADroneService.DroneService.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class defining the drone model.
 *
 * @author Chirita Gabriela
 */
@Data
@Document(collection = "drone")
@NoArgsConstructor
@AllArgsConstructor
public class DroneModel {
    /**
     * Member description
     */
    @Indexed(unique = true)
    String droneID;
    String droneModel;
    String droneStatus;
}
