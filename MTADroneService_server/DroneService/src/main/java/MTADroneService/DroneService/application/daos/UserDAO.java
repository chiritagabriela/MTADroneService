package MTADroneService.DroneService.application.daos;

import MTADroneService.DroneService.application.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<UserModel,String> {
    Optional<UserModel> findByJwtToken(String jwtToken);
    Optional<UserModel> findByUsername(String username);
}
