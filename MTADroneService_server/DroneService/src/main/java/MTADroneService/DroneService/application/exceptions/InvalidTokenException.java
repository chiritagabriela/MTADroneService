package MTADroneService.DroneService.application.exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(String s, RuntimeException e) {
        super(s);
    }
}
