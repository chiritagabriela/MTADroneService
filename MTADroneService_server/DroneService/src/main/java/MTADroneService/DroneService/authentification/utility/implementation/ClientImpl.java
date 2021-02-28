package MTADroneService.DroneService.authentification.utility.implementation;

import MTADroneService.DroneService.authentification.utility.Client;
import com.google.common.primitives.Ints;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientImpl implements Client {
    private Socket clientSocket;
    private OutputStream out;
    private BufferedReader in;

    @Override
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = clientSocket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    public void sendImageToServer(MultipartFile file) throws IOException {
        byte[] bytesImage = file.getBytes();
        int nrBytes = bytesImage.length;
        byte[] bytesNrBytes = Ints.toByteArray(nrBytes);
        out.write(bytesNrBytes);
        out.write(bytesImage);
    }
}
