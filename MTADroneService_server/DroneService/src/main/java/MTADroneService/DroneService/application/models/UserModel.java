package MTADroneService.DroneService.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class defining the user model.
 *
 * @author Chirita Gabriela
 */
@Data
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    /**
     * Member description
     */
    @Id
    private String userID;
    private String username;
    private String password;
    private String droneID;
    private String jwtToken;
    private List<String> roles;
    private String email;
}
