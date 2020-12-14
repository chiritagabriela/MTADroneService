package MTADroneService.DroneService.authentification.controller;

import MTADroneService.DroneService.authentification.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService tokenService;
    @GetMapping("/validate")
    public void validateToken(HttpServletRequest httpServletRequest) throws Exception{
        tokenService.validateToken("");
    }
}
