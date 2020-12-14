package MTADroneService.DroneService.authentification.services.implementation;

import MTADroneService.DroneService.authentification.daos.UserDAO;
import MTADroneService.DroneService.authentification.dtos.UserInfoDTO;
import MTADroneService.DroneService.authentification.models.UserModel;
import MTADroneService.DroneService.authentification.services.TokenService;
import MTADroneService.DroneService.authentification.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        checkNotNull(userInfoDTO.getPassword());
        System.out.println(userInfoDTO.getUsername());
        System.out.println(userInfoDTO.getUserID());
        System.out.println(userInfoDTO.getPassword());
        System.out.println(userInfoDTO.getDroneID());
        UserModel userModel = modelMapper.map(userInfoDTO, UserModel.class);
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));

        tokenService.generateNewToken(userModel);

        userDAO.save(userModel);
        modelMapper.map(userModel, userInfoDTO);
    }

    @Override
    public UserInfoDTO retriveUserInfo(String userId) {
        Optional<UserModel> userModelOptional = userDAO.findById(userId);
        if(userModelOptional.isPresent())
        {
            return modelMapper.map(userModelOptional.get(), UserInfoDTO.class);
        }
        return null;
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
