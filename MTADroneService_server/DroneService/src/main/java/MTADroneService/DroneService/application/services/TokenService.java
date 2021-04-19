package MTADroneService.DroneService.application.services;

import MTADroneService.DroneService.application.exceptions.InvalidTokenException;
import MTADroneService.DroneService.application.models.UserModel;

/**
 * Interface defining the TokenService.
 * It is used to implement the business logic behind the token controller.
 * @author Chirita Gabriela
 */
public interface TokenService {
    void validateToken(String jwtToken) throws InvalidTokenException;
    void generateNewToken(UserModel userModel);
}
