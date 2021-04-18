package MTADroneService.DroneService.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoDTO {
    DroneInfoDTO missionDroneInfo;
    String missionID;
    String missionType;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    Date missionDate;
    String missionStatus;
    String missionUserID;
}
