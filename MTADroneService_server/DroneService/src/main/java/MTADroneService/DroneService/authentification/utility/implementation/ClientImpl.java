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
        int currentLength = 0;
        int difference = -1;

        while (difference != 0) {
            difference = nrBytes - currentLength;
            if (difference < 8192 && difference != 0) {
                currentLength = currentLength + difference;
                out.write(getNextByteBlock(bytesImage,currentLength-difference,currentLength));
            }
            else{
                if(difference != 0) {
                    currentLength = currentLength + 8192;
                    out.write(getNextByteBlock(bytesImage, currentLength - 8192, currentLength));
                }
            }
        }
        byte[] stop = ("s").getBytes();
        out.write(stop);
    }
    private byte[] getNextByteBlock(byte[] totalBytes, int start, int end){
        byte[] currentBlock = new byte[8192];
        int iterator = 0;
        for(int i=start;i<end;i++){
            currentBlock[iterator] = totalBytes[i];
            iterator++;
        }
        return currentBlock;
    }
}
