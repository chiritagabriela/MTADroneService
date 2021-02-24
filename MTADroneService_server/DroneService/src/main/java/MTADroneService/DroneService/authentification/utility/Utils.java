package MTADroneService.DroneService.authentification.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return date;
    }
}
