package MTADroneService.DroneService.authentification.services;

import MTADroneService.DroneService.authentification.exceptions.InvalidTokenException;
import MTADroneService.DroneService.authentification.models.UserModel;

public interface TokenService {

    void validateToken(String jwtToken) throws InvalidTokenException;

    void generateNewToken(UserModel userModel);
}
