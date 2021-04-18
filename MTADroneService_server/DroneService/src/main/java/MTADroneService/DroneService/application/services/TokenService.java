package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.exceptions.InvalidTokenException;
import MTADroneService.DroneService.application.models.UserModel;

public interface TokenService {
    void validateToken(String jwtToken) throws InvalidTokenException;
    void generateNewToken(UserModel userModel);
}
