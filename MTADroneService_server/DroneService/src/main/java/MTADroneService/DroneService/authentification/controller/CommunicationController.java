package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.utility.Utils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.web.servlet.server.Session.SessionTrackingMode.URL;

@RestController
@RequestMapping("/communication")
@Slf4j
public class CommunicationController {

    @GetMapping(value = "/coordinates_to_go")
    public Map<String, Object> getCoordinatesToGo(){
        Map<String, Object> jsonObject = new HashMap();
        jsonObject.put("missionType", Utils.missionType);
        jsonObject.put("latitudeStart","");
        jsonObject.put("longitudeStart","");
        jsonObject.put("latitudeEnd",Utils.latitudeEnd);
        jsonObject.put("longitudeEnd",Utils.longitudeEnd);
        jsonObject.put("gabi","124");
        return jsonObject;
    }
}
