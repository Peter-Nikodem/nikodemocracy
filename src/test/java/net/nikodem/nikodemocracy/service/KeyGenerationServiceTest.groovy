package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/**
 * @author Peter Nikodem 
 */
class KeyGenerationServiceTest extends Specification {


    KeyGenerationService keyGenerationService = new KeyGenerationService()

    def "Voter key generation is random enough"(){
        given:  Set set = new HashSet()
                def N = 500_000
        when:   N.times {
                    set.add(keyGenerationService.randomAlphanumericString())
                }


        then:   set.size() == N
    }

}

