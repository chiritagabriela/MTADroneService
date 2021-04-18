package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.services.ServiceService;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/services")
@Slf4j
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSearchMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                              @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {

        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        checkNotNull(missionInfoDTO);
        MissionInfoToSend missionInfoToSend = new MissionInfoToSend(missionInfoDTO.getMissionLatitudeEnd(),
                missionInfoDTO.getMissionLongitudeEnd(), "SAR", "", false);
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SAR.toString());
        serviceService.createMission(missionInfoDTO,jwtToken,missionInfoToSend);
        return missionInfoDTO;
    }

    @PostMapping(value = "/surveil")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSurveilMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                               @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {

        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        checkNotNull(missionInfoDTO);
        MissionInfoToSend missionInfoToSend = new MissionInfoToSend(missionInfoDTO.getMissionLatitudeEnd(),
                missionInfoDTO.getMissionLongitudeEnd(), "SURVEIL", "" ,false);
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SURVEILLANCE.toString());
        serviceService.createMission(missionInfoDTO, jwtToken, missionInfoToSend);
        return missionInfoDTO;
    }

    @PostMapping(value = "/delivery")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createDeliveryMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                                @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {

        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        checkNotNull(missionInfoDTO);
        MissionInfoToSend missionInfoToSend = new MissionInfoToSend(missionInfoDTO.getMissionLatitudeEnd(),
                missionInfoDTO.getMissionLongitudeEnd(), "DELIVERY", "", false);
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.DELIVERY.toString());
        serviceService.createMission(missionInfoDTO, jwtToken, missionInfoToSend);
        return missionInfoDTO;
    }
}
