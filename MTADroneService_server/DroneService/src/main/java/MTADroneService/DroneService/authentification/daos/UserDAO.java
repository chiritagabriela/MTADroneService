package MTADroneService.DroneService.authentification.daos;

import MTADroneService.DroneService.authentification.models.UserModel;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<UserModel,String> {
    Optional<UserModel> findByJwtToken(String jwtToken);
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);
}
