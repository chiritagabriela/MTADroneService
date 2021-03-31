package MTADroneService.DroneService.authentification.utility;

import MTADroneService.DroneService.authentification.utility.implementation.ClientImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    //public static String SERVER_IP = "35.242.255.174";
    //public static int PORT = 443;
    //public static int imageNumber = 0;
    //public static List<Client> clients = new ArrayList<>();
    //public static int nrClients = 0;

    public static String missionType = "";
    public static String latitudeEnd = "";
    public static String longitudeEnd = "";
    public static String latitudeStart = "";
    public static String longitudeStart = "";
    public static String[] labels = {"person"};

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
        return new Date(System.currentTimeMillis());
    }

    /*
    public static void saveImage(byte[] byteImage) throws IOException {
        ImageIcon imageIcon = new ImageIcon(byteImage);
        Image image = imageIcon.getImage();
        RenderedImage rendered = null;
        if (image instanceof RenderedImage) {
            rendered = (RenderedImage)image;
        }
        else {
            BufferedImage buffered = new BufferedImage(
                    imageIcon.getIconWidth(),
                    imageIcon.getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g = buffered.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            rendered = buffered;
        }
        ImageIO.write(rendered, "JPG", new File(imageNumber+".jpg"));
        imageNumber = imageNumber + 1;
    }

    public static Client getClientConnection(String droneID){
        for (Client client : Utils.clients) {
            if (client.getDroneID().equals(droneID)) {
                return client;
            }
        }
        return null;

    }

    public static String serializeMessage(List<String> messages){
        String messageToSend = "!";
        for (String message : messages) {
            messageToSend = messageToSend + message + "!";
        }
        return messageToSend;
    }
*/
}
