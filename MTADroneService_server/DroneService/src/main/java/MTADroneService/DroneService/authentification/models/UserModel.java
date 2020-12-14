package MTADroneService.DroneService.authentification.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Document(collection = "user")
@NoArgsConstructor
public class UserModel implements Serializable, Persistable<String> {


    @Indexed(direction = IndexDirection.DESCENDING, unique = true)
    private String username;

    private String password;
    private String droneID;
    private String authToken;

    @Id
    private String userID;

    public String getDroneID() {
        return droneID;
    }

    public void setDroneID(String droneID) {
        this.droneID = droneID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserModel(String username, String password, String droneID, String authToken) {
        this.username = username;
        this.password = password;
        this.droneID = droneID;
        this.authToken = authToken;
        this.userID = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return userID;
    }

    @Override
    public boolean isNew() {
        return isNull(this.username);
    }
}
