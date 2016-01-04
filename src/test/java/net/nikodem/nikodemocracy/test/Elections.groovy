package net.nikodem.nikodemocracy.test

import static net.nikodem.nikodemocracy.test.Persons.*
/**
 * @author Peter Nikodem 
 */
class Elections {
    final def name;
    final def question;
    final def possibleAnswers
    final def votersEmails
    final def mailsToVoterKeys
    Elections(def name,def question, def possibleAnswers, def votersEmails, def mailsToVoterKeys){
        this.name = name
        this.question = question
        this.possibleAnswers = possibleAnswers
        this.votersEmails = votersEmails
        this.mailsToVoterKeys = mailsToVoterKeys
    }

    static def goodGuyEmails = GoodGuys.collect{it.email}
    static def goodGuyMailsToVoterKeys = GoodGuys.collectEntries({[it.email,it.voterKey]})

    public static Elections BearElection = new Elections("Bear with us!","Question! What kind of a bear is the best?", ["Black bear","That's debatable","Original question bear"],goodGuyEmails,goodGuyMailsToVoterKeys)

}
