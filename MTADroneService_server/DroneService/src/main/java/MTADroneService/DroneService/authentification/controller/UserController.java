package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import MTADroneService.DroneService.authentification.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user*")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO){
        checkNotNull(userInfoDTO);
        userService.createUser(userInfoDTO);
        return userInfoDTO;
    }


    @GetMapping("/info/{userId}")
    @PreAuthorize("hasAnyRole('USER')")
    public UserInfoDTO getUserInfo(@PathVariable String userId)
    {
        return userService.retrieveUserInfo(userId);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('USER')")
    public UserInfoDTO loginUser(@RequestBody UserInfoDTO userInfoDTO)
    {
        checkNotNull(userInfoDTO);
        return userService.loginUser(userInfoDTO);
    }
}
