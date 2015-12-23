package net.nikodem.nikodemocracy.integration

import net.nikodem.nikodemocracy.NikodemocracyApplication
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class ElectionProtocolRequirementsSpec extends Specification {
    def "spock runs with spring driver"(){
        expect: true
    }

    def "only authorized voters can vote"(){
    }

    def "no one can vote more than once"(){
    }

    //....
}