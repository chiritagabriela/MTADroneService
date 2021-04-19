package MTADroneService.DroneService.application.utility;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class defining the Utils of the server.
 * It provides all the additional members and methods that server needs.
 * @author Chirita Gabriela
 */
public class Utils {

    /**
     * Member description
     */

    //defining the type of missions available.
    public enum MissionTypes {
        SAR,
        DELIVERY,
        SURVEILLANCE
    }

    //defining the status of the mission.
    public enum MissionStatus {
        PREPARING,
        FLYING_TO_INTEREST_POINT,
        SEARCHING_PERSON,
        FLYING_TO_BASE,
        LANDING,
        FINISHED
    }

    //defining the status of the drone.
    public enum DroneStatus{
        AVAILABLE,
        BUSY
    }

    //defining the image number that is global to the server. This number is reset after each mission.
    public static AtomicInteger imageNumber = new AtomicInteger(0);
    //defining the concurrent map that holds the current position of the drone, along with other information.
    public static final Map<String, DroneCoordinates> currentPosition = new ConcurrentHashMap<>();
    //defining the concurrent map that holds the information of current mission.
    public static final Map<String, MissionInfoToSend> missionDetails = new ConcurrentHashMap<>();


    /**
     * Method updateDronePosition.
     * It's used to update the position of drone with every request received.
     * @param droneID it the ID of the drone.
     * @param droneCoordinates are the current coordinates of the drone.
     */
    public static void updateDronePosition(String droneID, DroneCoordinates droneCoordinates) {
        if(droneCoordinates != null) {
            currentPosition.get(droneID).setCurrentLongitude(droneCoordinates.currentLongitude);
            currentPosition.get(droneID).setCurrentLatitude(droneCoordinates.currentLatitude);
        }
    }

    /**
     * Method addMissionInfoToSend.
     * It's used to update the list of missions stored in the server.
     * @param droneID it the ID of the drone.
     * @param missionInfoToSend are the details of the current mission.
     */
    public static void addMissionInfoToSend(String droneID, MissionInfoToSend missionInfoToSend){
        missionDetails.put(droneID,missionInfoToSend);
    }

    /**
     * Method addNewImage.
     * It's used to update the list of images.
     * @param droneID it the ID of the drone.
     * @param imageName is the name of the image.
     */
    public static synchronized void addNewImage(String droneID, String imageName){
        currentPosition.get(droneID).getImages().add(imageName);
    }

    /**
     * Method updateMissionStatus.
     * It's used to update the status of the mission.
     * @param droneID it the ID of the drone.
     * @param newMissionStatus is the new status of the mission.
     */
    public static void updateMissionStatus(String droneID, String newMissionStatus){
        if(Utils.currentPosition.size() != 0) {
            Utils.currentPosition.get(droneID).setMissionStatus(newMissionStatus);
        }
    }

    /**
     * Method getDronePosition.
     * It's used to get the current position of the drone.
     * @param droneID it the ID of the drone.
     */
    public static DroneCoordinates getDronePosition(String droneID) {
        return currentPosition.get(droneID);
    }

    /**
     * Method getMissionInfoToSend.
     * It's used to get information about the current mission.
     * @param droneID it the ID of the drone.
     */
    public static MissionInfoToSend getMissionInfoToSend(String droneID){ return missionDetails.get(droneID); }

    /**
     * Method getCurrentDate.
     * It's used to get the current date.
     */
    public static String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    /**
     * Method saveImage.
     * It's used to save the image to server.
     * @param byteImage is the array of bites of the image.
     * @param imageName is the name of the image.
     * @param droneCoordinates are the current drone coordinates.
     */
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

    /**
     * Method deletePhoto.
     * It's used to delete an image from the server.
     * @param pictureName is the name of the image.
     */
    public static void deletePhoto(String pictureName){
        Path imagesPath = Paths.get("C:\\Users\\gabri\\Desktop\\Licenta\\MTADroneService\\MTADroneService\\MTADroneService_server\\DroneService\\images\\" + pictureName);
        try {
            Files.delete(imagesPath);
            System.out.println("File "
                    + imagesPath.toAbsolutePath().toString()
                    + " successfully removed!");
        } catch (IOException e) {
            System.err.println("Unable to delete "
                    + imagesPath.toAbsolutePath().toString()
                    + " due to...");
            e.printStackTrace();
        }
    }

    /**
     * Method deleteAllCurrentPhotos.
     * It's used to delete all the images from a certain drone.
     * @param droneID is the ID of the drone.
     */
    public static void deleteAllCurrentPhotos(String droneID){
        for(int i=0;i<Utils.getDronePosition(droneID).getImages().size();i++){
            deletePhoto(Utils.getDronePosition(droneID).getImages().get(i) + ".png");
        }
    }
}