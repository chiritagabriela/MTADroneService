package MTADroneService.DroneService.authentification.config.security;

import MTADroneService.DroneService.authentification.daos.UserDAO;
import MTADroneService.DroneService.authentification.exceptions.InvalidTokenException;
import MTADroneService.DroneService.authentification.exceptions.TokenAuthException;
import MTADroneService.DroneService.authentification.models.UserModel;
import MTADroneService.DroneService.authentification.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;


@Component
public class MTADroneServiceAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String token = (String) authentication.getCredentials();

        if (isEmpty(token)) {
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        Optional<UserModel> userModelOptional = userDAO.findByAuthToken(token);

        if (userModelOptional.isPresent()) {
            final UserModel userModel = userModelOptional.get();

            try {
                tokenService.validateToken(token);
            } catch (InvalidTokenException e) {
                userModel.setAuthToken(null);
                userDAO.save(userModel);
                return null;
            }

            return new User(username, "",
                    AuthorityUtils.createAuthorityList(
                            userModel.getRoles().stream()
                                    .map(roleName -> "ROLE_" + roleName)
                                    .toArray(String[]::new)
                    )
            );
        }

        try {
            throw new TokenAuthException("User not found for token :" + token);
        } catch (TokenAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
}
