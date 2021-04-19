package MTADroneService.DroneService.application.config.security;

import MTADroneService.DroneService.application.daos.UserDAO;
import MTADroneService.DroneService.application.exceptions.InvalidTokenException;
import MTADroneService.DroneService.application.exceptions.TokenAuthException;
import MTADroneService.DroneService.application.models.UserModel;
import MTADroneService.DroneService.application.services.TokenService;
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

/**
 * Class defining abstract user details for authentification provider.
 *
 * @author Chirita Gabriela
 */
@Component
public class MTADroneServiceAuthProvider extends AbstractUserDetailsAuthenticationProvider {


    /**
     * Member description
     */
    @Autowired
    UserDAO userDAO;

    @Autowired
    TokenService tokenService;

    /**
     * Method additionalAuthenticationChecks.
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException { }


    /**
     * Method retrieveUser.
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentification) throws AuthenticationException {
        final String token = (String) authentification.getCredentials();

        if (isEmpty(token)) {
            return new User(username, "", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        }

        Optional<UserModel> userModelOptional = userDAO.findByJwtToken(token);

        if (userModelOptional.isPresent()) {
            final UserModel userModel = userModelOptional.get();

            try {
                tokenService.validateToken(token);
            } catch (InvalidTokenException e) {
                userModel.setJwtToken(null);
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
