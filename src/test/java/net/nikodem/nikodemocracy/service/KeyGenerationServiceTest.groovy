package net.nikodem.nikodemocracy.service

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
                    set.add(keyGenerationService.generateRandomAlphanumericString())
                }
        then:   set.size() == N
    }

}

