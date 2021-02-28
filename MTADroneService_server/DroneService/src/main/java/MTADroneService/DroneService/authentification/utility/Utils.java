package MTADroneService.DroneService.authentification.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String SERVER_IP = "35.242.255.174";
    public static int PORT = 443;

    public enum MissionTypes {
        SAR,
        DELIVERY,
        SURVEILLANCE
    }

    public enum MissionStatus {
        PREPARING,
        FLYING_TO_INTEREST_POINT,
        SEARCHING_PERSON,
        FLYING_TO_BASE,
        SURVEIL_TERRAIN,
        COMING_BACK,
        LANDING
    }

    public enum DroneStatus{
        AVAILABLE,
        BUSY
    }
    public static Date getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return date;
    }
}
