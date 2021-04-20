package MTADroneService.DroneService.application.services.implementation;

import MTADroneService.DroneService.application.exceptions.InvalidTokenException;
import MTADroneService.DroneService.application.models.UserModel;
import MTADroneService.DroneService.application.services.AuthSigningKeyResolver;
import MTADroneService.DroneService.application.services.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class defining the TokenService service's implementation.
 * It is used to implement the business logic for token service.
 * @author Chirita Gabriela
 */
@Service
public class TokenServiceImpl implements TokenService {

    /**
     * Member description
     */
    @Autowired
    AuthSigningKeyResolver authSigningKeyResolver;

    final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    /**
     * Method validateToken.
     * It's used to validate the user's token.
     * @param jwtToken is the ID of the drone.
     */
    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {
        logger.info("Token service:token " + jwtToken + " validated.");
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigningKeyResolver)
                    .build()
                    .parse(jwtToken);
        }catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new InvalidTokenException("Invalid token.",e);
        }
    }

    /**
     * Method generateNewToken.
     * It's used to generate a new token for a user.
     * @param userModel is the user for which the token is generated.
     */
    @Override
    public void generateNewToken(UserModel userModel) {
        logger.info("Token service:token generated for user " + userModel.getUsername() + ".");
        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(userModel.getUsername())
                .signWith(authSigningKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        userModel.setJwtToken(jwtToken);
    }
}
