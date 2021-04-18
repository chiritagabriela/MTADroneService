package MTADroneService.DroneService.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "mission")
@NoArgsConstructor
@AllArgsConstructor
public class MissionModel {
    @Id
    String missionID;
    String missionDroneID;
    String missionType;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    Date missionDate;
    String missionStatus;
    String userID;
}
