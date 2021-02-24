package MTADroneService.DroneService.authentification.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    private String username;
    private String password;
    private String droneID;
    private String jwtToken;
    private List<String> roles;
    private String userID;
    private String email;
}
