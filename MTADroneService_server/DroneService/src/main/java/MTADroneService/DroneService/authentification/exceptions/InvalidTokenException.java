package MTADroneService.DroneService.authentification.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String s, RuntimeException e) {
        super(s);
    }
}
