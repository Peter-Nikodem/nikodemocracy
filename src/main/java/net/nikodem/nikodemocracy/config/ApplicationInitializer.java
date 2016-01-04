package net.nikodem.nikodemocracy.config;

import net.nikodem.nikodemocracy.authority.RegistrationAuthority;
import net.nikodem.nikodemocracy.model.dto.Admin;
import net.nikodem.nikodemocracy.model.dto.Election;
import net.nikodem.nikodemocracy.model.dto.ElectionBuilder;
import net.nikodem.nikodemocracy.repository.AdminRepository;
import net.nikodem.nikodemocracy.service.AdminDetailsService;
import net.nikodem.nikodemocracy.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Peter Nikodem
 */
@Configuration
public class ApplicationInitializer implements ServletContextInitializer {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminDetailsService userDetailsService;

    @Autowired
    RegistrationAuthority registrationAuthority;

    @Autowired
    ElectionService electionService;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        userDetailsService.registerNewUser("pete", "peternikodemjr@gmail.com", "hey");
        Admin pete = userDetailsService.loadUserByUsername("pete");
        List<String> emails = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        emails.add("peternikodemjr@gmail.com");
        answers.add("not much");
        answers.add("wazzuuup");

        Map<String,String> mailsToVoterKeys = new HashMap<>();
        mailsToVoterKeys.put("peternikodemjr@gmail.com", "fdfsdgdfhdfg");

        Election election = ElectionBuilder.election()
                .withName("Hello")
                .withQuestion("Sup?")
                .withPossibleAnswers(answers)
                .withEmailsOfEligibleVoters(emails)
                .withElectionAdmin(pete).build();

        electionService.createElection(election,mailsToVoterKeys);
    }
}
