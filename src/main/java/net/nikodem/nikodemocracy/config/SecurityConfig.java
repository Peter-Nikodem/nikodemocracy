package net.nikodem.nikodemocracy.config;

import net.nikodem.nikodemocracy.service.ElectionAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Peter Nikodem
 */
@Configuration
@Order(200)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ElectionAdminService adminService;

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        builder.userDetailsService(adminService).and().authenticationProvider(authenticationProvider);
    }

}
