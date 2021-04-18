package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.MissionDAO;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionDAO missionDAO;

    @Override
    public List<MissionModel> getMissionDetails(String userID) {
        return missionDAO.findByUserID(userID);
    }

    @Override
    public Optional<MissionModel> getMissionByID(String missionID) {
        return missionDAO.findById(missionID);
    }

    @Override
    public List<MissionModel> getMissionByDroneID(String droneID) {
        return missionDAO.findByMissionDroneID(droneID);
    }
}
