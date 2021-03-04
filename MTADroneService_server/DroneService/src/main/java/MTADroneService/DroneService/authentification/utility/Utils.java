package MTADroneService.DroneService.authentification.utility;

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
import java.util.Date;

public class Utils {

    public static String SERVER_IP = "127.0.0.1";
    public static int PORT = 9999;
    public static int imageNumber = 0;

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
}
