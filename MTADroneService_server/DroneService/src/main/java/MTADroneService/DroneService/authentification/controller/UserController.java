package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.daos.UserDAO;
import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import MTADroneService.DroneService.authentification.models.UserModel;
import MTADroneService.DroneService.authentification.services.TokenService;
import MTADroneService.DroneService.authentification.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHORIZATION;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static final String UNKNOWN_USERNAME_OR_BAD_PASSWORD = "Unknown username or bad password";
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public UserInfoDTO createUser(@RequestBody UserInfoDTO userInfoDTO){

        checkNotNull(userInfoDTO);

        userService.createUser(userInfoDTO);

        return userInfoDTO;
    }


    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public UserInfoDTO loginUser(@RequestBody UserInfoDTO userInfoDTO)
    {
        checkNotNull(userInfoDTO);
        return userService.loginUser(userInfoDTO);
    }
}
