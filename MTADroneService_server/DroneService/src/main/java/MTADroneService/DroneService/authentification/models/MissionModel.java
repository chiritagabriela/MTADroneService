package MTADroneService.DroneService.authentification.models;

import MTADroneService.DroneService.authentification.services.RetrieveInfoDrone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "mission")
@NoArgsConstructor
@AllArgsConstructor
public class MissionModel {
    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    String missionID;
    String missionDroneID;
    String missionType;
    String missionLongitudeStart;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    String missionLatitudeStart;
    RetrieveInfoDrone retrieveInfoDrone;
    Date missionDate;
    String missionStatus;
}
