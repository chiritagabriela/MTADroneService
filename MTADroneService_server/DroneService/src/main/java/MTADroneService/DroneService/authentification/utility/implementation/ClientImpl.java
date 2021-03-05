package MTADroneService.DroneService.authentification.utility.implementation;

import MTADroneService.DroneService.authentification.utility.Client;
import MTADroneService.DroneService.authentification.utility.Utils;
import com.google.common.primitives.Ints;
import com.sun.mail.iap.ByteArray;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ClientImpl implements Client {

    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;
    private String droneID;

    @Override
    public void startConnection(String ip, int port, String droneID) throws IOException {
        clientSocket = new Socket(ip, port);
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
        this.droneID = droneID;
    }

    @Override
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    public void receiveImageFromServer() throws IOException {
        byte[] nrBytes = new byte[4];
        int okTotalLength = in.read(nrBytes);
        int imageLen = bytesToInt(nrBytes,findPadding(nrBytes));
        byte[] byteImage = new byte[imageLen];
        int currentImgLen = 0;
        int getOut = 0;
        while(getOut == 0) {
            int okChunksLength = in.read(nrBytes);
            int chunkLen = bytesToInt(nrBytes,findPadding(nrBytes));
            byte[] receivedImage = new byte[chunkLen];
            int okPhoto = in.read(receivedImage,0,chunkLen);
            concatenateImageChunks(byteImage, receivedImage, currentImgLen);
            currentImgLen = currentImgLen + chunkLen;
            if(currentImgLen == imageLen){
                getOut = 1;
            }
        }
        Utils.saveImage(byteImage);
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

    private void concatenateImageChunks(byte[] imageArray, byte[] receivedImage, int byteImgLen) {
        int cnt = 0;
        for (int i=byteImgLen; i<byteImgLen + receivedImage.length;i++){
            imageArray[i] = receivedImage[cnt];
            cnt = cnt + 1;
        }
    }

    private int bytesToInt(byte[] array, int nrBytes){
        int nr = 0;
        for(int i=0;i<nrBytes;i++){
            nr = nr + (int)Math.pow(10,nrBytes-1-i)*((int)array[i]-48);
        }
        return nr;
    }

    private int findPadding(byte[] array) {
        int nr = 0;
        for (byte b : array) {
            if (b == 33) {
                nr++;
            }
        }
        return array.length-nr;
    }

    @Override
    public String getDroneID(){
        return this.droneID;
    }

    @Override
    public void sendMessageToServer(String message) throws IOException {
        this.out.write(message.getBytes(StandardCharsets.UTF_8));
    }
}
