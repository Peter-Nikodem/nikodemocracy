package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import net.nikodem.nikodemocracy.authority.RegistrationAuthority
import net.nikodem.nikodemocracy.model.dto.Admin
import net.nikodem.nikodemocracy.model.dto.Election
import net.nikodem.nikodemocracy.model.dto.ElectionBuilder
import net.nikodem.nikodemocracy.model.dto.Vote
import net.nikodem.nikodemocracy.repository.PresenceRepository
import net.nikodem.nikodemocracy.repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared
import spock.lang.Specification

import static net.nikodem.nikodemocracy.test.Elections.BearElection
import static net.nikodem.nikodemocracy.test.Persons.Alice
import static net.nikodem.nikodemocracy.test.Persons.GoodGuys

/**
 * @author Peter Nikodem 
 */
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class VoteServiceTest extends Specification {

    @Autowired
    VoteService voteService

    @Autowired
    PresenceRepository presenceRepository

    @Autowired
    RegistrationAuthority registrationAuthority

    @Autowired
    VoteRepository voteRepository

    @Autowired
    AdminDetailsService adminService

    @Autowired
    ElectionService electionService;

    Admin alicesAccount

    Election alicesElection


    Map<String,String> goodGuysMailsToVoterKeys = GoodGuys.collectEntries{guy -> [guy.email,guy.voterKey]}


    def setup(){
        adminService.registerNewUser(Alice.username, Alice.email, Alice.password)
        alicesAccount = adminService.loadUserByUsername(Alice.username)
        alicesElection = ElectionBuilder.election()
                .withName(BearElection.name)
                .withQuestion(BearElection.question)
                .withPossibleAnswers(BearElection.possibleAnswers)
                .withEmailsOfEligibleVoters(BearElection.votersEmails)
                .withElectionAdmin(alicesAccount).build()

        KeyGenerationService keyGenerationService = Mock()
        def x = 1
        keyGenerationService.randomAlphanumericString() >> {
            x++
        }
        registrationAuthority.setKeyGenerationService(keyGenerationService)
        registrationAuthority.registerElection(alicesElection)
    }

    def "vote"(){
        when:
        def voterKey = presenceRepository.findAll().get(0).voterKey
        def vote = new Vote(alicesElection,voterKey, '23', "Black bear");
        presenceRepository.findAll().forEach{println(it)}
        voteService.submitVote(vote)
        presenceRepository.findAll().forEach{println(it)}

        then:
        voteRepository.count()>0

    }


}
