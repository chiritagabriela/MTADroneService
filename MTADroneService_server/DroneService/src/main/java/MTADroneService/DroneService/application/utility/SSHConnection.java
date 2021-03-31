package MTADroneService.DroneService.authentification.utility;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class SSHConnection {

    String host;
    String user;
    String password;

    public SSHConnection(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public void sendCommandToDrone(String command){
        try{
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session=jsch.getSession(this.user, this.host, 22);
            session.setPassword(this.password);
            session.setConfig(config);
            session.connect();

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
