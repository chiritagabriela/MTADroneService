package MTADroneService.DroneService.authentification.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO {
    private String userID;
    private String username;
    private String password;
    private String authToken;
    private String droneID;
    private List<String> roles;

    public UserInfoDTO(String userID, String username, String password, String authToken, String droneID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.authToken = authToken;
        this.droneID= droneID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getDroneID() {
        return droneID;
    }

    public void setDroneID(String droneID) {
        this.droneID = droneID;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
