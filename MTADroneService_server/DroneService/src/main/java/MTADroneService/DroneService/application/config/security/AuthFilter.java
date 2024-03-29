package MTADroneService.DroneService.application.config.security;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Class defining authentification filter of the application.
 * This class checks if an user has the authority to enter website.
 * @author Chirita Gabriela
 */

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * Auth filter class constructor.
     */
    public AuthFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        logger.info("Auth filter constructor with defaultFilterProcessesUrl called.");
    }

    /**
     * Auth filter class constructor.
     */
    public AuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        logger.info("Auth filter constructor with requiresAuthenticationRequestMatcher called.");
    }


    /**
     * Method attemptAuthentication.
     * Verifies the header of the request and give users a role.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        logger.info("Attempt authentification.");
        String tokenUnstrapped = request.getHeader(AUTHORIZATION);
        String token = StringUtils.removeStart(Optional.ofNullable(tokenUnstrapped).orElse(""), "Bearer").trim();

        Authentication authentication;
        if (isEmpty(token)){
            authentication = new UsernamePasswordAuthenticationToken("guest", "");
            logger.info("Guest authentification token created.");
        } else {
            authentication = new UsernamePasswordAuthenticationToken("user", token);
            logger.info("User authentification token created.");
        }
        return getAuthenticationManager().authenticate(authentication);
    }

    /**
     * Method successfulAuthentication.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Successful authentification.");
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    /**
     * Method unsuccessfulAuthentication.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        logger.info("Unsuccessful authentification.");
        response.setStatus(SC_FORBIDDEN);
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("error", failed.getCause());
        jsonObject.put("errorMessage", failed.getMessage());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print(jsonObject.toString());
        response.getWriter().flush();
    }

}
