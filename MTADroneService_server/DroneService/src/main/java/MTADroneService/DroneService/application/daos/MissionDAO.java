package MTADroneService.DroneService.application.daos;

import MTADroneService.DroneService.application.models.MissionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MissionDAO extends MongoRepository<MissionModel,String> {
    List<MissionModel> findByUserID(String userID);
    List<MissionModel> findByMissionDroneID(String droneID);
}
