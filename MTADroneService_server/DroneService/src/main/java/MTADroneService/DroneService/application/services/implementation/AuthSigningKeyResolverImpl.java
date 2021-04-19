package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.services.AuthSigningKeyResolver;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

import static java.util.Objects.isNull;

/**
 * Class defining the AuthSigningKeyResolver service's implementation.
 * It is used to get the key used to encrypt the JWT Token.
 * @author Chirita Gabriela
 */
@Component
public class AuthSigningKeyResolverImpl implements AuthSigningKeyResolver {

    /**
     * Member description
     */
    @Value("${jwt.secret.key}")
    String secretKeyString;
    private SecretKey secretKey;

    /**
     * Method getSecretKey.
     * It's used get the secret key for jwt token's encryption.
     */
    @Override
    public SecretKey getSecretKey() {
        if(isNull(secretKey)){
            this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(this.secretKeyString.getBytes()));
        }
        return this.secretKey;
    }

    /**
     * Method resolveSigningKey.
     * It's used to resolve the signing key.
     */
    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        return getSecretKey();
    }

    /**
     * Method resolveSigningKey.
     * It's used to resolve the signing key.
     */
    @Override
    public Key resolveSigningKey(JwsHeader header, String plaintext) {
        return getSecretKey();
    }
}
