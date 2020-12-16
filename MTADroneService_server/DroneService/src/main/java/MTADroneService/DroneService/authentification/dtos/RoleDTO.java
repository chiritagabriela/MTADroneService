package MTADroneService.DroneService.authentification.dtos;

import lombok.Data;

@Data
public class RoleDTO {
    private String role;
    private String userID;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
