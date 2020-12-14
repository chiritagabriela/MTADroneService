package MTADroneService.DroneService.authentification.daos;

import MTADroneService.DroneService.authentification.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<UserModel,String> {
    Optional<UserModel> findByAuthToken(String authToken);
    Optional<UserModel> findByUsername(String username);

}
