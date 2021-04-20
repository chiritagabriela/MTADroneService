package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.DroneDAO;
import MTADroneService.DroneService.application.daos.MissionDAO;
import MTADroneService.DroneService.application.daos.UserDAO;
import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.models.UserModel;
import MTADroneService.DroneService.application.services.ServiceService;
import MTADroneService.DroneService.application.utility.DroneCoordinates;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;
import MTADroneService.DroneService.application.utility.Utils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.List;

/**
 * Class defining the ServiceService service's implementation.
 * It is used to implement the business logic for service service.
 * @author Chirita Gabriela
 */
@Service
public class ServiceServiceImpl implements ServiceService {

    /**
     * Member description
     */
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MissionDAO missionDAO;

    @Autowired
    DroneDAO droneDAO;

    @Autowired
    UserDAO userDAO;

    final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

    /**
     * Method createMission.
     * It's used to create a mission after the information received from interface.
     * @param missionInfoDTO is the mission information from interface.
     * @param jwtToken is the jwtToken of the user.
     * @param missionInfoToSend is the mission information stored in server.
     */
    @Override
    public void createMission(MissionInfoDTO missionInfoDTO, String jwtToken, MissionInfoToSend missionInfoToSend){

        MissionModel missionModel = modelMapper.map(missionInfoDTO, MissionModel.class);
        missionModel.setMissionStatus(Utils.MissionStatus.PREPARING.toString());
        List<DroneModel> droneModelList = droneDAO.findByDroneStatus("AVAILABLE");
        Optional<UserModel> optionalUserModel = userDAO.findByJwtToken(jwtToken);
        if(optionalUserModel.isPresent()){
            UserModel userModel = optionalUserModel.get();
            missionModel.setUserID(userModel.getUserID());
            logger.info("Service service:mission created for user " + userModel.getUsername() + ".");
            if(droneModelList.size() != 0) {
                DroneModel newDrone = droneModelList.get(0);
                Utils.addMissionInfoToSend(newDrone.getDroneID(),missionInfoToSend);
                newDrone.setDroneStatus(Utils.DroneStatus.BUSY.toString());
                droneDAO.save(newDrone);
                droneDAO.deleteByDroneIDAndDroneStatus(droneModelList.get(0).getDroneID(), Utils.DroneStatus.AVAILABLE.toString());
                missionModel.setMissionDroneID(newDrone.getDroneID());
                missionDAO.save(missionModel);
                DroneCoordinates droneCoordinates = new DroneCoordinates();
                Utils.currentPosition.put(newDrone.getDroneID(),droneCoordinates);
            }
        }
    }
}
