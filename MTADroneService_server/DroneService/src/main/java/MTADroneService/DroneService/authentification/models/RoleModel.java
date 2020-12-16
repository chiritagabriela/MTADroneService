package MTADroneService.DroneService.authentification.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "role")
@EqualsAndHashCode(callSuper = true)
public class RoleModel extends GenericUserModel{
    private String role;

    public RoleModel(String role) {
        super();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
