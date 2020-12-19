package MTADroneService.DroneService.authentification.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private String password;
    private String jwtToken;
    private String droneID;
    private List<String> roles;
    private String email;
}
