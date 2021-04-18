package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.DroneDAO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneServiceImpl implements DroneService {
    @Autowired
    DroneDAO droneDAO;

    @Override
    public Optional<DroneModel> getDroneInfo(String missionDroneID) {
        return droneDAO.findByDroneID(missionDroneID);
    }

    @Override
    public List<DroneModel> getAllDrones() {
        return droneDAO.findAll();
    }
}
