package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.services.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Class defining the token controller.
 * This controller is used to perform operations related to the JWT Token.
 * @author Chirita Gabriela
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    /**
     * Member description
     */
    @Autowired
    TokenService tokenService;

    final Logger logger = LoggerFactory.getLogger(TokenController.class);

    /**
     * Method validateToken.
     * Validates the jwt token that is provided from the user in order to authenticate to server.
     */
    @GetMapping("/validate")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception{
        logger.info("Token controller:trying to validate token.");
        String authHeader = httpServletRequest.getHeader(AUTHORIZATION);
        String token = null;
        if (!isEmpty(authHeader)) {
            token = authHeader.split("\\s")[1];
        } else {
            return;
        }
        tokenService.validateToken(token);

    }
}
