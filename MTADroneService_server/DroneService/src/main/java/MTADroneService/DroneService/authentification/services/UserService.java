package MTADroneService.DroneService.authentification.services;

import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;

public interface UserService {
    public void createUser(UserInfoDTO userInfoDTO);
    public UserInfoDTO loginUser(UserInfoDTO userInfoDTO);
    public UserInfoDTO retriveUserInfo(String userId);
}
