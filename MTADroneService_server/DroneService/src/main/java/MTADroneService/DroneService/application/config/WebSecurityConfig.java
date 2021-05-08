package MTADroneService.DroneService.application.config;

import MTADroneService.DroneService.application.config.security.AuthFilter;
import MTADroneService.DroneService.application.config.security.MTADroneServiceAuthProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;


/**
 * Class defining configurations for web security adapter.
 *
 * @author Chirita Gabriela
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Member description
     */
    @Autowired
    MTADroneServiceAuthProvider mtaDroneServiceAuthProvider;

    final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    /**
     * Method configure.
     * Configures the web security adapter of the application.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Configuring web security adapter.");
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(mtaDroneServiceAuthProvider)
                .addFilterBefore(authFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .cors();

    }

    /**
     * Method authFilter.
     * Defines the authentification filter of the application.
     */
    public AuthFilter authFilter() throws Exception {
        logger.info("Auth filter created.");
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher("/user/**"),
                new AntPathRequestMatcher("/token/**"),
                new AntPathRequestMatcher("/services/**"),
                new AntPathRequestMatcher("/missions/**"),
                new AntPathRequestMatcher("/communication/**"),
                new AntPathRequestMatcher("/drone/**")
        );

        AuthFilter authFilter = new AuthFilter(orRequestMatcher);
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }

}
