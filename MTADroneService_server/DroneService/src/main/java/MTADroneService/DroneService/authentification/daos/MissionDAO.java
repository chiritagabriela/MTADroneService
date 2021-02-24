package MTADroneService.DroneService.authentification.daos;

import MTADroneService.DroneService.authentification.models.MissionModel;
import MTADroneService.DroneService.authentification.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionDAO extends MongoRepository<MissionModel,String> {
}
