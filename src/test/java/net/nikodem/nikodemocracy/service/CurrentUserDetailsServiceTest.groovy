package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import net.nikodem.nikodemocracy.model.dto.CurrentUser
import net.nikodem.nikodemocracy.model.exception.UsernameAlreadyTakenException
import net.nikodem.nikodemocracy.repository.ElectionAdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class CurrentUserDetailsServiceTest extends Specification {

    @Autowired
    CurrentUserDetailsService userDetailsService;

    @Autowired
    ElectionAdminRepository registeredAccountRepository

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

    def setup(){
        registeredAccountRepository.deleteAll()
    }

    def "new user's email is saved"(){
        given:  userDetailsService.registerNewUser('Alice','alice.nikodemocracy@gmail.com','DownTheRabbitHole')
        when:   def alicesAccount = userDetailsService.loadUserByUsername('Alice')
        then:   alicesAccount.email == 'alice.nikodemocracy@gmail.com'
    }

    def "new user's password is encrypted"(){
        given:  'Alice correctly registers a new account with Election Admin role'
                userDetailsService.registerNewUser('Alice','alice.nikodemocracy@gmail.com','DownTheRabbitHole')
        when:   'saved account is returned'
                def alicesAccount = userDetailsService.loadUserByUsername('Alice')
        then:   'password is successfully encrypted'
                //interestingly, comparing stored password with newly generated one fails.
                // While it's not really unexpected, why?
                alicesAccount.password != 'DownTheRabbitHole'
                passwordEncoder.matches('DownTheRabbitHole',alicesAccount.password)
    }

    def 'attempting to register a new account with already registered username throws exception'(){
        given:  'Alice correctly registers a new account with Election Admin Role'
                userDetailsService.registerNewUser('Alice','alice.nikodemocracy@gmail.com','DownTheRabbitHole')
                def alicesOriginalAccount = userDetailsService.loadUserByUsername('Alice')
        when:   'someone attempts to register a new account with the same username'
                userDetailsService.registerNewUser('Alice','alice@someoneelse.com',"ILikeBigButts")
        then:   'exception is thrown and original account remains unchanged'
                thrown(UsernameAlreadyTakenException)
                userDetailsService.loadUserByUsername('Alice') == alicesOriginalAccount
    }

    def 'attempting to log in with an unregistered username throws exception'(){
        when:   userDetailsService.loadUserByUsername('Eva')
        then:   thrown(UsernameNotFoundException)
    }


}
