package net.nikodem.nikodemocracy.config;

import net.nikodem.nikodemocracy.model.jpa.ElectionAdmin;
import net.nikodem.nikodemocracy.repository.ElectionAdminRepository;
import net.nikodem.nikodemocracy.service.CurrentUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Peter Nikodem
 */
@Configuration
public class ApplicationInitializer implements ServletContextInitializer {

    @Autowired
    ElectionAdminRepository electionAdminRepository;

    @Autowired
    CurrentUserDetailsService userDetailsService;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (electionAdminRepository.count()==0){
            userDetailsService.registerNewUser("peter","peternikodemjr@gmail.com","hello");
        }
    }
}
