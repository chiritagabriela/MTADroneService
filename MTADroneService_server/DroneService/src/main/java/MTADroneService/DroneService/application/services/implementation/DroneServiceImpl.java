package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.DroneDAO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class defining the DroneService service's implementation.
 * It is used to implement the business logic for drone service.
 * @author Chirita Gabriela
 */
@Service
public class DroneServiceImpl implements DroneService {

    /**
     * Member description
     */
    @Autowired
    DroneDAO droneDAO;

    /**
     * Method getDroneInfo.
     * It's used to retrieve information about a drone after its ID.
     * @param missionDroneID is the ID of the drone.
     */
    @Override
    public Optional<DroneModel> getDroneInfo(String missionDroneID) {
        return droneDAO.findByDroneID(missionDroneID);
    }

    /**
     * Method getAllDrones.
     * It's used to retrieve information about all the drones.
     */
    @Override
    public List<DroneModel> getAllDrones() {
        return droneDAO.findAll();
    }
}
