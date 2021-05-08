package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.dtos.MissionInfoDTO;
import MTADroneService.DroneService.application.services.ServiceService;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Class defining the services controller.
 * This controller helps at creating missions.
 * @author Chirita Gabriela
 */
@RestController
@RequestMapping("/services")
@Slf4j
public class ServiceController {

    /**
     * Member description
     */
    @Autowired
    ServiceService serviceService;

    final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * Method createSearchMission.
     * Creates a SAR mission.
     * @param tokenUnstrapped is jwt token of the user, including its header.
     * @param  missionInfoDTO provides the information from interface.
     */
    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSearchMission(@RequestBody MissionInfoDTO missionInfoDTO,

                                              @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {
        logger.info("Service controller:Mission SAR on the verge of being created for user with token unstrapped " + tokenUnstrapped + ".");
        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        checkNotNull(missionInfoDTO);
        MissionInfoToSend missionInfoToSend = new MissionInfoToSend(missionInfoDTO.getMissionLatitudeEnd(),
                missionInfoDTO.getMissionLongitudeEnd(), "SAR", "", false);
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SAR.toString());
        serviceService.createMission(missionInfoDTO,jwtToken,missionInfoToSend);
        return missionInfoDTO;
    }

    /**
     * Method createSurveilMission.
     * Creates a SURVEIL mission.
     * @param tokenUnstrapped is jwt token of the user, including its header.
     * @param  missionInfoDTO provides the information from interface.
     */
    @PostMapping(value = "/surveil")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createSurveilMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                               @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {

        logger.info("Service controller:Mission SURVEIL on the verge of being created for user with token unstrapped " + tokenUnstrapped + ".");
        String jwtToken = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();
        checkNotNull(missionInfoDTO);
        MissionInfoToSend missionInfoToSend = new MissionInfoToSend(missionInfoDTO.getMissionLatitudeEnd(),
                missionInfoDTO.getMissionLongitudeEnd(), "SURVEIL", "" ,false);
        missionInfoDTO.setMissionDate(Utils.getCurrentDate());
        missionInfoDTO.setMissionType(Utils.MissionTypes.SURVEILLANCE.toString());
        serviceService.createMission(missionInfoDTO, jwtToken, missionInfoToSend);
        return missionInfoDTO;
    }

    /**
     * Method createDeliveryMission.
     * Creates a DELIVERY mission.
     * @param tokenUnstrapped is jwt token of the user, including its header.
     * @param  missionInfoDTO provides the information from interface.
     */
    @PostMapping(value = "/delivery")
    @PreAuthorize("hasAnyRole('USER')")
    public MissionInfoDTO createDeliveryMission(@RequestBody MissionInfoDTO missionInfoDTO,
                                                @RequestHeader (name="Authorization") String tokenUnstrapped) throws IOException {
        logger.info("Service controller:Mission DELIVERY on the verge of being created for user with token unstrapped " + tokenUnstrapped + ".");
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
