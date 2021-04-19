package MTADroneService.DroneService.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Class defining the mission model.
 *
 * @author Chirita Gabriela
 */
@Data
@Document(collection = "mission")
@NoArgsConstructor
@AllArgsConstructor
public class MissionModel {
    /**
     * Member description
     */
    @Id
    String missionID;
    String missionDroneID;
    String missionType;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    String missionDate;
    String missionStatus;
    String userID;
}
