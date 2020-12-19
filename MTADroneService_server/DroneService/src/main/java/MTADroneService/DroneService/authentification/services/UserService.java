package MTADroneService.DroneService.authentification.services;

import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import org.apache.catalina.User;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;

public interface UserService {
    void createUser(UserInfoDTO userInfoDTO);
    UserInfoDTO loginUser(UserInfoDTO userInfoDTO);
}
