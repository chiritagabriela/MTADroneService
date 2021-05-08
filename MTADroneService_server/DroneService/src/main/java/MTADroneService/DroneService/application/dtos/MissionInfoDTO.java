package MTADroneService.DroneService.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class defining the mission DTO.
 *
 * @author Chirita Gabriela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionInfoDTO {
    /**
     * Member description
     */
    DroneInfoDTO missionDroneInfo;
    String missionID;
    String missionType;
    String missionLatitudeEnd;
    String missionLongitudeEnd;
    String missionDate;
    String missionStatus;
    String missionUserID;
}
