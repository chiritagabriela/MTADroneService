package MTADroneService.DroneService.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class defining the user DTO.
 *
 * @author Chirita Gabriela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    /**
     * Member description
     */
    private String username;
    private String password;
    private String jwtToken;
    private String droneID;
    private List<String> roles;
    private String email;
    private String userID;
}
