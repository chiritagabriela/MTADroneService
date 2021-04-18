package MTADroneService.DroneService.application.utility;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {

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
        LANDING,
        FINISHED
    }

    public enum DroneStatus{
        AVAILABLE,
        BUSY
    }

    public static AtomicInteger imageNumber = new AtomicInteger(0);
    public static final Map<String, DroneCoordinates> currentPosition = new ConcurrentHashMap<>();
    public static final Map<String, MissionInfoToSend> missionDetails = new ConcurrentHashMap<>();

    public static void updateDronePosition(String droneID, DroneCoordinates droneCoordinates) {
        if(droneCoordinates != null) {
            currentPosition.get(droneID).setCurrentLongitude(droneCoordinates.currentLongitude);
            currentPosition.get(droneID).setCurrentLatitude(droneCoordinates.currentLatitude);
        }
    }

    public static void addMissionInfoToSend(String droneID, MissionInfoToSend missionInfoToSend){
        missionDetails.put(droneID,missionInfoToSend);
    }

    public static synchronized void addNewImage(String droneID, String imageName){
        currentPosition.get(droneID).getImages().add(imageName);
    }

    public static void updateMissionStatus(String droneID, String newMissionStatus){
        if(Utils.currentPosition.size() != 0) {
            Utils.currentPosition.get(droneID).setMissionStatus(newMissionStatus);
        }
    }

    public static DroneCoordinates getDronePosition(String droneID) {
        return currentPosition.get(droneID);
    }

    public static MissionInfoToSend getMissionInfoToSend(String droneID){ return missionDetails.get(droneID); }

    public static void removeMissionInfoToSend(String droneID){Utils.missionDetails.remove(droneID);}

    public static Date getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(System.currentTimeMillis());
    }

    public static void saveImage(byte[] byteImage, String imageName, DroneCoordinates droneCoordinates) {
        Thread th1 = new Thread(new Runnable() {
            @SneakyThrows
            public void run() {
                ImageIcon imageIcon = new ImageIcon(byteImage);
                Image image = imageIcon.getImage();
                RenderedImage rendered = null;
                if (image instanceof RenderedImage) {
                    rendered = (RenderedImage) image;
                } else {
                    BufferedImage buffered = new BufferedImage(
                            imageIcon.getIconWidth(),
                            imageIcon.getIconHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );
                    Graphics2D g = buffered.createGraphics();
                    g.drawImage(image, 0, 0, null);
                    g.setFont(g.getFont().deriveFont(20f));
                    String stringToWrite = "Lat:" + droneCoordinates.getCurrentLatitude() + "-Lon:" + droneCoordinates.getCurrentLongitude();
                    g.drawString(stringToWrite, 50, 50);
                    g.dispose();
                    rendered = buffered;
                }

                String windowsPath = "C:\\Users\\gabri\\Desktop\\Licenta\\MTADroneService\\MTADroneService\\MTADroneService_server\\DroneService\\images\\";
                try {
                    ImageIO.write(rendered, "PNG", new File(windowsPath + imageName + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        th1.start();
    }

    public static File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            return null;
        } else {
            return new File(resource.toURI());
        }
    }
}