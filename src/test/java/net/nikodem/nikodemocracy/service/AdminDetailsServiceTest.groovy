package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import net.nikodem.nikodemocracy.model.exception.UsernameAlreadyTakenException
import net.nikodem.nikodemocracy.repository.AdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static net.nikodem.nikodemocracy.test.Persons.*

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class AdminDetailsServiceTest extends Specification {

    @Autowired
    AdminDetailsService adminDetailsService;

    @Autowired
    AdminRepository registeredAccountRepository

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder()

    def setup(){
        registeredAccountRepository.deleteAll()
    }

    def "new user's email is saved"(){
        given:  adminDetailsService.registerNewUser(Alice.username,Alice.email,Alice.password)
        when:   def alicesAccount = adminDetailsService.loadUserByUsername(Alice.username)
        then:   alicesAccount.email == Alice.email
    }

    def "new user's password is encrypted"(){
        given:  'Bob correctly registers a new account with Election Admin role'
                adminDetailsService.registerNewUser(Bob.username,Bob.email,Bob.password)
        when:   'saved account is returned'
                def bobsAccount = adminDetailsService.loadUserByUsername(Bob.username)
        then:   'password is successfully encrypted'
                //interestingly, comparing stored password with newly generated one fails.
                // While it's not really unexpected, why?
                bobsAccount.password != Bob.password
                passwordEncoder.matches(Bob.password,bobsAccount.password)
    }

    def 'attempting to register a new account with already registered username throws exception'(){
        given:  'Cecil correctly registers a new account with Election Admin Role'
                adminDetailsService.registerNewUser(Cecil.username,Cecil.email,Cecil.password)
                def cecilsOriginalAccount = adminDetailsService.loadUserByUsername(Cecil.username)
        when:   'someone attempts to register a new account with the same username'
                adminDetailsService.registerNewUser(Cecil.username,Eva.email,Eva.password)
        then:   'exception is thrown and original account remains unchanged'
                thrown(UsernameAlreadyTakenException)
                adminDetailsService.loadUserByUsername(Cecil.username) == cecilsOriginalAccount
    }

    def 'attempting to log in with an unregistered username throws exception'(){
        when:   adminDetailsService.loadUserByUsername(Eva.username)
        then:   thrown(UsernameNotFoundException)
    }


}
