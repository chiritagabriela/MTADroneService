package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.config.WebSecurityConfig;
import MTADroneService.DroneService.application.daos.MissionDAO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.services.DroneService;
import MTADroneService.DroneService.application.services.MissionService;
import MTADroneService.DroneService.application.utility.DroneCoordinates;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Class defining the communication controller.
 * This controller is in charge of the communication between drone,server,interface and cloud VM.
 * @author Chirita Gabriela
 */
@RestController
@RequestMapping("/communication")
@Slf4j
public class CommunicationController {

    /**
     * Member description
     */
    final Logger logger = LoggerFactory.getLogger(CommunicationController.class);

    @Autowired
    MissionService missionService;

    @Autowired
    MissionDAO missionDAO;

    @Autowired
    DroneService droneService;

    /**
     * Method getCoordinatesToGo.
     * It's used by drone in order to see when a mission was started.
     * @param droneID is the unique ID of the drone.
     */
    @GetMapping(value = "/coordinates_to_go/{droneID}")
    public Map<String, Object> getCoordinatesToGo(@PathVariable String droneID){

        logger.info("Communication controller:Coordinates taken by drone " + droneID + ".");
        MissionInfoToSend missionInfoToSend = Utils.getMissionInfoToSend(droneID);

        if(missionInfoToSend == null) {
            Map<String, Object> jsonObject = new HashMap();
            jsonObject.put("missionType", "");
            jsonObject.put("latitudeEnd","");
            jsonObject.put("longitudeEnd","");
            return jsonObject;
        }
        else{
            Map<String, Object> jsonObject = new HashMap();
            jsonObject.put("missionType", missionInfoToSend.getMissionType());
            jsonObject.put("latitudeEnd",missionInfoToSend.getMissionLatitude());
            jsonObject.put("longitudeEnd",missionInfoToSend.getMissionLongitude());
            return jsonObject;
        }
    }

    /**
     * Method storeCurrentLocation.
     * It's used to store the current location of the drone in server.
     * @param droneID is the unique ID of the drone.
     * @param coordinates are the coordinates of the drone.
     */
    @PostMapping(value = "/store_current_location/{droneID}", consumes = "application/json")
    public void storeCurrentLocation(@PathVariable String droneID, @RequestBody DroneCoordinates coordinates){
       logger.info("Communication controller:Location stored for drone " + droneID + ".");
       Utils.updateDronePosition(droneID, coordinates);
    }

    /**
     * Method updateMissionStatus.
     * It's used by drone to update mission's status whenever needed.
     * @param droneID is the unique ID of the drone.
     * @param newMissionStatus is the new status of the mission.
     */
    @PostMapping(value = "/update_mission_status/{droneID}/{newMissionStatus}", consumes = "application/json")
    public void updateMissionStatus(@PathVariable String droneID, @PathVariable String newMissionStatus){
        logger.info("Communication controller:Status updated to " + newMissionStatus + " for drone " + droneID + ".");
        List<MissionModel> missionModelList = missionService.getMissionByDroneID(droneID);
        if(missionModelList.size() != 0){
            for (MissionModel missionModel : missionModelList) {
                if (!missionModel.getMissionStatus().equals("FINISHED")) {
                    missionModel.setMissionStatus(newMissionStatus);
                    missionDAO.save(missionModel);
                    Utils.updateMissionStatus(droneID,newMissionStatus);
                }
            }
        }
    }

    /**
     * Method handleFileUpload.
     * It's used by the cloud VM in order to upload photos of found people to server.
     * @param file is the file the needs to be uploaded.
     * @param droneID is the unique ID of the drone.
     */
    @RequestMapping(value="/upload/{droneID}", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("files") MultipartFile file, @PathVariable String droneID){
        logger.info("Communication controller:File uploaded for drone " + droneID + ".");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String imageName = String.valueOf(Utils.imageNumber.getAndIncrement());
                Utils.saveImage(bytes, imageName, Utils.getDronePosition(droneID));
                Utils.addNewImage(droneID, imageName);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return null;
    }


    /**
     * Method getVideoURL.
     * It's used by the cloud VM to get the video URL from server.
     */
    @RequestMapping(value="/get_video_url", method = RequestMethod.GET)
    public Map<String, Object>  getVideoURL() {
        logger.info("Communication controller:Video URL taken.");
        Map<String, Object> jsonObject = new HashMap();
        int size = Utils.missionDetails.size();
        int notFound = 1;

        if (size == 0) {
            jsonObject.put("videoURL", "");
            jsonObject.put("droneID", "");
        } else {
            List<DroneModel> droneModelsList = droneService.getAllDrones();
            for (DroneModel droneModel : droneModelsList) {
                if(Utils.missionDetails.get(droneModel.getDroneID()) != null) {
                    if (!Utils.missionDetails.get(droneModel.getDroneID()).getIsTaken()) {
                        jsonObject.put("videoURL", Utils.missionDetails.get(droneModel.getDroneID()).getVideoURL());
                        jsonObject.put("droneID", droneModel.getDroneID());
                        notFound = 0;
                    }
                }
            }
            if(notFound == 1) {
                jsonObject.put("videoURL", "");
                jsonObject.put("droneID", "");
            }
        }
        return jsonObject;
    }

    /**
     * Method setVideoURL.
     * It's used by drone to store the video URL to server.
     * @param droneID is the unique ID of the drone.
     * @param videoURL is the live video URL that needs to be stored to server.
     */
    @RequestMapping(value="/set_video_url/{droneID}/{videoURL}", method = RequestMethod.POST)
    public void setVideoURL(@PathVariable String droneID, @PathVariable String videoURL) {
        logger.info("Communication controller:Video URL set by drone " + droneID + ".");
        Utils.missionDetails.get(droneID).setVideoURL(videoURL);
    }


    /**
     * Method getImage.
     * It's used by interface to get photos from server.
     * @param imageName represents the name of the image.
     */
    @RequestMapping(value = "/get_image/{imageName}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getImage(@PathVariable String imageName){
        logger.info("Communication controller:Image " + imageName + " taken.");
        Map<String, String> jsonMap = new HashMap<>();
        String windowsPath = "C:\\Users\\gabri\\Desktop\\Licenta\\MTADroneService\\MTADroneService\\MTADroneService_server\\DroneService\\images\\";
        try {
            File file = new File(windowsPath + imageName);
            if (file != null) {
                String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
                jsonMap.put("content", encodeImage);
            } else {
                jsonMap.put("content", null);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonMap;
    }

    /**
     * Method getStatus.
     * It's used by interface to get mission's status from server.
     * @param droneID is the unique ID of the drone.
     */
    @RequestMapping(value="/get_mission_status/{droneID}", method = RequestMethod.GET)
    public Map<String, Object> getStatus(@PathVariable String droneID){
        logger.info("Communication controller:Mission status retrieved for drone " + droneID + ".");
        Map<String, Object> jsonObject = new HashMap();
        jsonObject.put("missionStatus", Utils.getDronePosition(droneID).getMissionStatus());
        return jsonObject;
    }
}
