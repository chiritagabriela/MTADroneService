package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.daos.UserDAO;
import MTADroneService.DroneService.application.dtos.DroneInfoDTO;
import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.models.UserModel;
import MTADroneService.DroneService.application.services.DroneService;
import MTADroneService.DroneService.application.services.MissionService;
import MTADroneService.DroneService.application.utility.DroneCoordinates;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/missions")
@Slf4j
public class MissionController {

    @Autowired
    private MissionService missionService;

    @Autowired
    private DroneService droneService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDAO userDAO;

    @GetMapping(value = "/current_mission")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO getMissionDetails(@RequestHeader(name = "Authorization") String tokenUnstrapped) {
        List<MissionModel> missionModelListToShow = new ArrayList<>();
        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Optional<UserModel> optionalUserModel = userDAO.findByJwtToken(jwtToken);
        if (optionalUserModel.isPresent()) {

            UserModel userModel = optionalUserModel.get();
            List<MissionModel> missionModelList = missionService.getMissionDetails(userModel.getUserID());

            if (missionModelList.size() != 0) {
                for (MissionModel model : missionModelList) {
                    if (!model.getMissionStatus().equals("FINISHED")) {
                        Optional<DroneModel> droneModelOptional = droneService.getDroneInfo(model.getMissionDroneID());
                        if (droneModelOptional.isPresent()) {
                            DroneModel droneModel = droneModelOptional.get();
                            MissionInfoDTO missionInfoDTO = modelMapper.map(model, MissionInfoDTO.class);
                            DroneInfoDTO droneInfoDTO = modelMapper.map(droneModel, DroneInfoDTO.class);
                            missionInfoDTO.setMissionDroneInfo(droneInfoDTO);
                            return missionInfoDTO;
                        }
                    }
                }
            }
        }
        return null;
    }

    @GetMapping(value = "/all_missions")
    @PreAuthorize("hasAnyRole('USER')")
    public List<MissionModel> getAllMissions(@RequestHeader(name = "Authorization") String tokenUnstrapped) {
        List<MissionModel> missionModelListToShow = new ArrayList<>();
        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Optional<UserModel> optionalUserModel = userDAO.findByJwtToken(jwtToken);
        if (optionalUserModel.isPresent()) {

            UserModel userModel = optionalUserModel.get();
            List<MissionModel> missionModelList = missionService.getMissionDetails(userModel.getUserID());

            if (missionModelList.size() != 0) {
                for (MissionModel model : missionModelList) {
                    if (model.getMissionStatus().equals("FINISHED")) {
                        missionModelListToShow.add(model);
                    }
                }
            }
            return missionModelListToShow;
        }
        return null;
    }

}
