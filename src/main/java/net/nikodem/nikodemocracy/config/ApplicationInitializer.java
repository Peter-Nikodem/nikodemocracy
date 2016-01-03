package net.nikodem.nikodemocracy.config;

import net.nikodem.nikodemocracy.repository.AdminRepository;
import net.nikodem.nikodemocracy.service.AdminDetailsService;
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
    AdminRepository adminRepository;

    @Autowired
    AdminDetailsService userDetailsService;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (adminRepository.count()==0){
            userDetailsService.registerNewUser("peter","peternikodemjr@gmail.com","hello");
        }
    }
}
