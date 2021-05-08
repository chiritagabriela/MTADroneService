package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.DroneDAO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.services.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

    /**
     * Method getDroneInfo.
     * It's used to retrieve information about a drone after its ID.
     * @param missionDroneID is the ID of the drone.
     */
    @Override
    public Optional<DroneModel> getDroneInfo(String missionDroneID) {
        logger.info("Drone service:drone info retrieved for mission with ID:"+missionDroneID+".");
        return droneDAO.findByDroneID(missionDroneID);
    }

    /**
     * Method getAllDrones.
     * It's used to retrieve information about all the drones.
     */
    @Override
    public List<DroneModel> getAllDrones() {
        logger.info("Drone service:drone info retrieved for all drones.");
        return droneDAO.findAll();
    }
}
