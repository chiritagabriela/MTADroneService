package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.daos.MissionDAO;
import MTADroneService.DroneService.application.models.DroneModel;
import MTADroneService.DroneService.application.models.MissionModel;
import MTADroneService.DroneService.application.services.DroneService;
import MTADroneService.DroneService.application.services.MissionService;
import MTADroneService.DroneService.application.utility.DroneCoordinates;
import MTADroneService.DroneService.application.utility.MissionInfoToSend;
import MTADroneService.DroneService.application.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/communication")
@Slf4j
public class CommunicationController {

    @Autowired
    MissionService missionService;

    @Autowired
    MissionDAO missionDAO;

    @Autowired
    DroneService droneService;

    @GetMapping(value = "/coordinates_to_go/{droneID}")
    public Map<String, Object> getCoordinatesToGo(@PathVariable String droneID){

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

    @PostMapping(value = "/store_current_location/{droneID}", consumes = "application/json")
    public void storeCurrentLocation(@PathVariable String droneID, @RequestBody DroneCoordinates coordinates){
       Utils.updateDronePosition(droneID, coordinates);
    }

    @PostMapping(value = "/update_mission_status/{droneID}/{newMissionStatus}", consumes = "application/json")
    public void updateMissionStatus(@PathVariable String droneID, @PathVariable String newMissionStatus){
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

    @RequestMapping(value="/upload/{droneID}", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("files") MultipartFile file, @PathVariable String droneID){
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

    @RequestMapping(value="/store_video_url/{droneID}/{videoURL}", method = RequestMethod.POST)
    public @ResponseBody String storeVideoURL(@PathVariable String droneID, @PathVariable String videoURL){
        Utils.getMissionInfoToSend(droneID).setVideoURL(videoURL);
        return null;
    }

    @RequestMapping(value="/get_video_url", method = RequestMethod.GET)
    public Map<String, Object>  getVideoURL() {
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


    @RequestMapping(value="/set_video_url/{droneID}/{videoURL}", method = RequestMethod.POST)
    public void setVideoURL(@PathVariable String droneID, @PathVariable String videoURL) {
        Utils.missionDetails.get(droneID).setVideoURL(videoURL);
    }


    @RequestMapping(value = "/get_image/{imageName}", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getImage(@PathVariable String imageName) throws IOException, URISyntaxException {
        Map<String, String> jsonMap = new HashMap<>();
        String windowsPath = "C:\\Users\\gabri\\Desktop\\Licenta\\MTADroneService\\MTADroneService\\MTADroneService_server\\DroneService\\";
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

    @RequestMapping(value="/get_mission_status/{droneID}", method = RequestMethod.GET)
    public Map<String, Object> getStatus(@PathVariable String droneID){
        Map<String, Object> jsonObject = new HashMap();
        jsonObject.put("missionStatus", Utils.getDronePosition(droneID).getMissionStatus());
        return jsonObject;
    }
}
