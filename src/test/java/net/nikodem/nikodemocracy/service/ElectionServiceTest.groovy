package net.nikodem.nikodemocracy.service

import net.nikodem.nikodemocracy.NikodemocracyApplication
import net.nikodem.nikodemocracy.model.dto.Admin
import net.nikodem.nikodemocracy.model.dto.Election
import net.nikodem.nikodemocracy.model.dto.ElectionBuilder
import net.nikodem.nikodemocracy.model.exception.ElectionNameAlreadyTakenException
import net.nikodem.nikodemocracy.repository.AdminRepository
import net.nikodem.nikodemocracy.repository.AnswerRepository
import net.nikodem.nikodemocracy.repository.ElectionRepository
import net.nikodem.nikodemocracy.repository.VoterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static net.nikodem.nikodemocracy.test.Persons.*
import static net.nikodem.nikodemocracy.test.Elections.*


/**
 *
 * @author Peter Nikodem
 */
@IntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = NikodemocracyApplication.class)
class ElectionServiceTest extends Specification {
    @Autowired
    ElectionService electionService

    @Autowired
    ElectionRepository electionRepository

    @Autowired
    VoterRepository voterRepository

    @Autowired
    AnswerRepository answerRepository

    @Autowired
    AdminDetailsService adminService

    @Autowired
    AdminRepository adminRepository

    Admin alicesAccount

    Election newAlicesElection

    Map<String,String> goodGuysMailsToVoterKeys = GoodGuys.collectEntries{guy -> [guy.email,guy.voterKey]}

    def setup() {
        answerRepository.deleteAll()
        voterRepository.deleteAll()
        electionRepository.deleteAll()
        adminRepository.deleteAll()
        adminService.registerNewUser(Alice.username, Alice.email, Alice.password)
        alicesAccount = adminService.loadUserByUsername(Alice.username)
        newAlicesElection = ElectionBuilder.election()
                .withName(BearElection.name)
                .withQuestion(BearElection.question)
                .withPossibleAnswers(BearElection.possibleAnswers)
                .withEmailsOfEligibleVoters(BearElection.votersEmails)
                .withElectionAdmin(alicesAccount).build()

    }

    def "saved election is fully retrievable"(){
        when:   electionService.createElection(getNewAlicesElection(),BearElection.mailsToVoterKeys)
                def foundElection = electionService.findUsersElections(Alice.username).get(0)
        then:   foundElection.name == BearElection.name
                foundElection.question==BearElection.question
                foundElection.possibleAnswers == BearElection.possibleAnswers
                foundElection.emailsOfEligibleVoters == BearElection.votersEmails
    }

    def "election is saved with all parts"(){
        when:   electionService.createElection(newAlicesElection,goodGuysMailsToVoterKeys)
        then:   electionRepository.count() == 1
                voterRepository.count() == 3
                answerRepository.count() == 3
    }

    def "attempting to save the election with already existing name results in exception"(){
        when:   electionService.createElection(newAlicesElection,new HashMap<String,String>())
                electionService.createElection(newAlicesElection,goodGuysMailsToVoterKeys)
        then:   thrown(ElectionNameAlreadyTakenException)
    }

    def "finding by short url works correctly"(){
        when:   electionService.createElection(newAlicesElection,goodGuysMailsToVoterKeys)
        then:   electionService.findElectionByShortUrl(newAlicesElection.shortUrl).name == BearElection.name
    }




}
