package MTADroneService.DroneService.application.daos;

import MTADroneService.DroneService.application.models.DroneModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Class defining the drone DAO.
 *
 * @author Chirita Gabriela
 */
@Repository
public interface DroneDAO extends MongoRepository<DroneModel,String> {
    List<DroneModel> findByDroneStatus(String droneStatus);
    void deleteByDroneIDAndDroneStatus(String droneID, String droneStatus);
    Optional<DroneModel> findByDroneID(String droneID);
}
