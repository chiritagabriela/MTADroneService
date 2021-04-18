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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MissionDAO missionDAO;

    @Autowired
    DroneDAO droneDAO;

    @Autowired
    UserDAO userDAO;

    @Override
    public void createMission(MissionInfoDTO missionInfoDTO, String jwtToken,
                              MissionInfoToSend missionInfoToSend) throws IOException {

        MissionModel missionModel = modelMapper.map(missionInfoDTO, MissionModel.class);
        missionModel.setMissionStatus(Utils.MissionStatus.PREPARING.toString());
        List<DroneModel> droneModelList = droneDAO.findByDroneStatus("AVAILABLE");

        Optional<UserModel> optionalUserModel = userDAO.findByJwtToken(jwtToken);
        if(optionalUserModel.isPresent()){
            UserModel userModel = optionalUserModel.get();
            missionModel.setUserID(userModel.getUserID());
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
