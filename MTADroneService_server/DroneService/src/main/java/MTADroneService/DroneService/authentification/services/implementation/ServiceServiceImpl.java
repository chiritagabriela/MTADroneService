package MTADroneService.DroneService.authentification.services.implementation;

import MTADroneService.DroneService.authentification.daos.DroneDAO;
import MTADroneService.DroneService.authentification.daos.MissionDAO;
import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import MTADroneService.DroneService.authentification.models.DroneModel;
import MTADroneService.DroneService.authentification.models.MissionModel;
import MTADroneService.DroneService.authentification.services.ServiceService;
import MTADroneService.DroneService.authentification.utility.Client;
import MTADroneService.DroneService.authentification.utility.Utils;
import MTADroneService.DroneService.authentification.utility.implementation.ClientImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MissionDAO missionDAO;

    @Autowired
    DroneDAO droneDAO;

    @Override
    public void createMission(MissionInfoDTO missionInfoDTO, MultipartFile file) throws IOException {
        Client client = new ClientImpl();
        client.startConnection(Utils.SERVER_IP,Utils.PORT);
        client.receiveImageFromServer();
        MissionModel missionModel = modelMapper.map(missionInfoDTO, MissionModel.class);
        missionModel.setMissionID(UUID.randomUUID().toString());
        missionModel.setMissionStatus(Utils.MissionStatus.PREPARING.toString());
        List<DroneModel> droneModelList = droneDAO.findByDroneStatus("AVAILABLE");
        DroneModel newDrone = droneModelList.get(0);
        newDrone.setDroneStatus(Utils.DroneStatus.BUSY.toString());
        droneDAO.save(newDrone);
        droneDAO.deleteByDroneIDAndDroneStatus(droneModelList.get(0).getDroneID(),Utils.DroneStatus.AVAILABLE.toString());
        missionModel.setMissionDroneID(newDrone.getDroneID());
        missionDAO.save(missionModel);
    }
}
