package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.dtos.UserInfoDTO;

/**
 * Interface defining the UserService.
 * It is used to implement the business logic behind the user controller.
 * @author Chirita Gabriela
 */
public interface UserService {
    void createUser(UserInfoDTO userInfoDTO);
    UserInfoDTO loginUser(UserInfoDTO userInfoDTO);
}
