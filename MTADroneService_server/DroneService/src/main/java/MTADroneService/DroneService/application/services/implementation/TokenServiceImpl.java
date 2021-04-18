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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    AuthSigningKeyResolver authSigningKeyResolver;

    @Override
    public void validateToken(String jwtToken) throws InvalidTokenException {
        try {
            Jwts.parserBuilder()
                    .setSigningKeyResolver(authSigningKeyResolver)
                    .build()
                    .parse(jwtToken);
        }catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new InvalidTokenException("Invalid token.",e);
        }
    }

    @Override
    public void generateNewToken(UserModel userModel) {
        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(userModel.getUsername())
                .signWith(authSigningKeyResolver.getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
        userModel.setJwtToken(jwtToken);
    }
}
