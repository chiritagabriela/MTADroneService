package MTADroneService.DroneService.authentification.daos;

import MTADroneService.DroneService.authentification.models.DroneModel;
import MTADroneService.DroneService.authentification.models.MissionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneDAO extends MongoRepository<DroneModel,String> {
    List<DroneModel> findByDroneStatus(String droneStatus);
    void deleteByDroneIDAndDroneStatus(String droneID, String droneStatus);
}
