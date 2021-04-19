package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.daos.DroneDAO;
import MTADroneService.DroneService.application.dtos.DroneInfoDTO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.services.DroneService;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Class defining the drone controller.
 * This controller is in charge of the communication between drone and server.
 * @author Chirita Gabriela
 */
@RestController
@RequestMapping("/drone")
@Slf4j
public class DroneController {

    /**
     * Member description
     */
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DroneDAO droneDAO;

    @Autowired
    DroneService droneService;

    /**
     * Method getCurrentPosition.
     * It's used by interface in order to get drone's current position, status and photos.
     * @param droneID is the unique ID of the drone.
     */
    @GetMapping(value = "/get_current_position/{droneID}")
    @PreAuthorize("hasAnyRole('USER')")
    DroneInfoDTO getCurrentPosition(@PathVariable String droneID){
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Optional<DroneModel> droneModelOptional = droneService.getDroneInfo(droneID);
        if(droneModelOptional.isPresent()){
            DroneModel droneModel = droneModelOptional.get();
            DroneInfoDTO droneInfoDTO = modelMapper.map(droneModel, DroneInfoDTO.class);
            droneInfoDTO.setCurrentDroneCoordinates(Utils.getDronePosition(droneID));
            if(droneInfoDTO.getCurrentDroneCoordinates() != null) {
                if (droneInfoDTO.getCurrentDroneCoordinates().getMissionStatus().equals(Utils.MissionStatus.FINISHED.toString())) {
                    List<DroneModel> droneModelList = droneDAO.findByDroneStatus("BUSY");
                    if (droneModelList.size() != 0) {
                        DroneModel newDrone = droneModelList.get(0);
                        newDrone.setDroneStatus(Utils.DroneStatus.AVAILABLE.toString());
                        Utils.deleteAllCurrentPhotos(droneID);
                        Utils.missionDetails.clear();
                        Utils.currentPosition.clear();
                        Utils.imageNumber.set(0);
                        droneDAO.save(newDrone);
                        droneDAO.deleteByDroneIDAndDroneStatus(droneModelList.get(0).getDroneID(), Utils.DroneStatus.BUSY.toString());
                    }
                }
            }
            return droneInfoDTO;
        }
        return null;
    }

}
