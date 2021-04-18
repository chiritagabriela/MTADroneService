package MTADroneService.DroneService.application.controller;

import MTADroneService.DroneService.application.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/validate")
    @PreAuthorize("hasAnyRole('USER','ANONYMOUS')")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception{
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
