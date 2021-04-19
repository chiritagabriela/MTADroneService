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
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class defining the UserService service's implementation.
 * It is used to implement the business logic for user service.
 * @author Chirita Gabriela
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Member description
     */
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Method createUser.
     * It's used to create a new user.
     * @param userInfoDTO is the information needed from the interface in order to create a new user.
     */
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

    /**
     * Method loginUser.
     * It's used to login a user.
     * @param userInfoDTO is the information needed from the interface in order to login a user.
     */
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
