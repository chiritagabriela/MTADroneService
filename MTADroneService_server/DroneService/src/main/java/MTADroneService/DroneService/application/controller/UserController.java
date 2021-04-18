package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.dtos.UserInfoDTO;
import MTADroneService.DroneService.application.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static final String UNKNOWN_USERNAME_OR_BAD_PASSWORD = "Unknown username or bad password";

    @Autowired
    UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO){
        checkNotNull(userInfoDTO);
        userService.createUser(userInfoDTO);
        return userInfoDTO;
    }


    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public UserInfoDTO loginUser(@RequestBody UserInfoDTO userInfoDTO) {
        checkNotNull(userInfoDTO);
        return userService.loginUser(userInfoDTO);
    }
}
