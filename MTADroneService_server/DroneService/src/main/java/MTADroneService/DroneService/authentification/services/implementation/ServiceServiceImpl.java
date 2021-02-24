package MTADroneService.DroneService.authentification.services.implementation;

import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import MTADroneService.DroneService.authentification.models.MissionModel;
import MTADroneService.DroneService.authentification.services.ServiceService;
import MTADroneService.DroneService.authentification.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void createMission(MissionInfoDTO missionInfoDTO) {
        MissionModel missionModel = modelMapper.map(missionInfoDTO, MissionModel.class);
        missionModel.setMissionID(UUID.randomUUID().toString());
        System.out.println(Utils.getCurrentDate());
        missionModel.setMissionDate(Utils.getCurrentDate());

    }
}
