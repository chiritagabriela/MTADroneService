package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.dtos.UserInfoDTO;

public interface UserService {
    void createUser(UserInfoDTO userInfoDTO);
    UserInfoDTO loginUser(UserInfoDTO userInfoDTO);
}
