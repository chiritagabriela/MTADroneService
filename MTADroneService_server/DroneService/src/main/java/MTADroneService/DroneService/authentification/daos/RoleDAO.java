package MTADroneService.DroneService.authentification.daos;


import MTADroneService.DroneService.authentification.models.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends MongoRepository<RoleModel, String> {
        Optional<RoleModel> findByRole(String role);
}


