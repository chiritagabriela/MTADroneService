package MTADroneService.DroneService.authentification.services.implementation;

import MTADroneService.DroneService.authentification.daos.MissionDAO;
import MTADroneService.DroneService.authentification.daos.UserDAO;
import MTADroneService.DroneService.authentification.dtos.MissionInfoDTO;
import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import MTADroneService.DroneService.authentification.models.MissionModel;
import MTADroneService.DroneService.authentification.models.UserModel;
import MTADroneService.DroneService.authentification.services.TokenService;
import MTADroneService.DroneService.authentification.services.UserService;

import MTADroneService.DroneService.authentification.utility.Utils;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(UserInfoDTO userInfoDTO) {

        List<String> roles = new ArrayList<>();
        roles.add("USER");
        roles.add("ANONYMOUS");
        checkNotNull(userInfoDTO.getPassword());

        UserModel userModel = modelMapper.map(userInfoDTO, UserModel.class);
        userModel.setUserID(UUID.randomUUID().toString());
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));

        tokenService.generateNewToken(userModel);
        userModel.setRoles(roles);

        userDAO.save(userModel);
        userInfoDTO.setPassword("");
        modelMapper.map(userModel, userInfoDTO);
    }

    @Override
    public UserInfoDTO loginUser(UserInfoDTO userInfoDTO) {
        Optional<UserModel> userModelOptional = userDAO.findByUsername(userInfoDTO.getUsername());
        if(userModelOptional.isPresent()){
            UserModel userModel = userModelOptional.get();
            if(bCryptPasswordEncoder.matches(userInfoDTO.getPassword(),userModel.getPassword())) {
                return modelMapper.map(userModel,UserInfoDTO.class);
            }
            else{
                throw new BadCredentialsException("Username or password is incorrect.");
            }
        }
        else{
            throw new BadCredentialsException("Username or password is incorrect.");
        }
    }
}
