package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.MissionDAO;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class defining the MissionService service's implementation.
 * It is used to implement the business logic for mission service.
 * @author Chirita Gabriela
 */
@Service
public class MissionServiceImpl implements MissionService {

    /**
     * Member description
     */
    @Autowired
    private MissionDAO missionDAO;

    /**
     * Method getMissionDetails.
     * It's used to retrieve information about user's active mission.
     * @param userID is the ID of the user.
     */
    @Override
    public List<MissionModel> getMissionDetails(String userID) {
        return missionDAO.findByUserID(userID);
    }

    /**
     * Method getMissionByID.
     * It's used to retrieve information about a mission after its ID.
     * @param missionID is the ID of the mission.
     */
    @Override
    public Optional<MissionModel> getMissionByID(String missionID) {
        return missionDAO.findById(missionID);
    }

    /**
     * Method getMissionByDroneID.
     * It's used to retrieve information about a mission after its drone ID.
     * @param droneID is the ID of the drone.
     */
    @Override
    public List<MissionModel> getMissionByDroneID(String droneID) {
        return missionDAO.findByMissionDroneID(droneID);
    }
}
