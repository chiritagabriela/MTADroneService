package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import MTADroneService.DroneService.authentification.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user*")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO){
        checkNotNull(userInfoDTO);
        userService.createUser(userInfoDTO);
        return userInfoDTO;
    }

    @GetMapping("/info/{userId}")
    public UserInfoDTO getUserInfo(@PathVariable String userId)
    {
        return userService.retriveUserInfo(userId);
    }
    @PostMapping("/login")
    public UserInfoDTO loginUser(@RequestBody UserInfoDTO userInfoDTO)
    {
        checkNotNull(userInfoDTO);
        return userService.loginUser(userInfoDTO);
    }
}
