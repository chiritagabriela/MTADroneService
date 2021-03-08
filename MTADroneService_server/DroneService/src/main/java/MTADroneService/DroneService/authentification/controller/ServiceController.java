package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import MTADroneService.DroneService.authentification.services.ServiceService;
import MTADroneService.DroneService.authentification.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/services")
@Slf4j
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSearchMission(@RequestBody MissionInfoDTO missionInfoDTO) throws IOException {
        checkNotNull(missionInfoDTO);
        Utils.latitudeEnd = missionInfoDTO.getMissionLatitudeEnd();
        Utils.longitudeEnd = missionInfoDTO.getMissionLongitudeEnd();
        Utils.missionType = "search";
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SAR.toString());
        serviceService.createMission(missionInfoDTO);
        return missionInfoDTO;
    }

    @PostMapping(value = "/surveil")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSurveilMission(@RequestBody MissionInfoDTO missionInfoDTO) throws IOException {
        checkNotNull(missionInfoDTO);
        Utils.latitudeEnd = missionInfoDTO.getMissionLatitudeEnd();
        Utils.longitudeEnd = missionInfoDTO.getMissionLongitudeEnd();
        Utils.missionType = "surveil";
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SURVEILLANCE.toString());
        serviceService.createMission(missionInfoDTO);
        return missionInfoDTO;
    }

    @PostMapping(value = "/delivery")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createDeliveryMission(@RequestBody MissionInfoDTO missionInfoDTO) throws IOException {
        checkNotNull(missionInfoDTO);
        Utils.latitudeEnd = missionInfoDTO.getMissionLatitudeEnd();
        Utils.longitudeEnd = missionInfoDTO.getMissionLongitudeEnd();
        Utils.latitudeStart = missionInfoDTO.getMissionLatitudeStart();
        Utils.longitudeStart = missionInfoDTO.getMissionLongitudeStart();
        Utils.missionType = "delivery";
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.DELIVERY.toString());
        serviceService.createMission(missionInfoDTO);
        return missionInfoDTO;
    }
}
