package MTADroneService.DroneService.authentification.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Client {
    void startConnection(String ip, int port, String droneID) throws IOException;
    void stopConnection() throws IOException;
    void receiveImageFromServer() throws IOException;
    String getDroneID();
    void sendMessageToServer(String message) throws IOException;
}
