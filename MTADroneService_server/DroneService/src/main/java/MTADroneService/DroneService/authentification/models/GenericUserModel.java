package MTADroneService.DroneService.authentification.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.UUID;

@Data
public class GenericUserModel implements Serializable {

    @Id
    private String userID;

    public GenericUserModel(){
        this.userID = UUID.randomUUID().toString();
    }
}
