package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.daos.UserDAO;
import MTADroneService.DroneService.application.dtos.UserInfoDTO;
import MTADroneService.DroneService.application.models.UserModel;
import MTADroneService.DroneService.application.services.TokenService;
import MTADroneService.DroneService.application.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.*;

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
