package MTADroneService.DroneService.authentification.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Client {
    void startConnection(String ip, int port) throws IOException;
    void stopConnection() throws IOException;
    void sendImageToServer(MultipartFile file) throws IOException;
}
