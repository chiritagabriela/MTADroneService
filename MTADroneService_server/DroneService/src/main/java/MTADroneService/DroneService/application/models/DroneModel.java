package MTADroneService.DroneService.authentification.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "drone")
@NoArgsConstructor
@AllArgsConstructor
public class DroneModel {
    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    String droneID;
    String droneModel;
    String droneStatus;
}
