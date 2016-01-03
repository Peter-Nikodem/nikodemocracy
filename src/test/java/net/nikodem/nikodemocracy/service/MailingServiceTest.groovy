package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import net.nikodem.nikodemocracy.model.dto.InvitationEmail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class MailingServiceTest extends Specification {

    @Autowired
    MailingService mailingService

    def "mailPreparation"(){
        when: mailingService.sendEmail(new InvitationEmail('peternikodemjr@gmail.com','mgiofdkpgofdkogdfjog',"Bear with us!","localhost:8080/#!election/9xi90dsfh3"))
        then: true
    }
}
