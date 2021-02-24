package MTADroneService.DroneService.authentification.daos;

import MTADroneService.DroneService.authentification.models.DroneModel;
import MTADroneService.DroneService.authentification.models.MissionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneDAO extends MongoRepository<DroneModel,String> {
}
