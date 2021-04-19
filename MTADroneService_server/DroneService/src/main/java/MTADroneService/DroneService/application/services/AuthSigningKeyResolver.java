package MTADroneService.DroneService.application.services;

import io.jsonwebtoken.SigningKeyResolver;

import javax.crypto.SecretKey;

/**
 * Interface defining the AuthSigningKeyResolver.
 * It is used to get the key used to encrypt the JWT Token.
 * @author Chirita Gabriela
 */
public interface AuthSigningKeyResolver extends SigningKeyResolver {
    SecretKey getSecretKey();
}
