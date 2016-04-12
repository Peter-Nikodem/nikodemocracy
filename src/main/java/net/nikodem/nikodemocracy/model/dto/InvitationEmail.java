package net.nikodem.nikodemocracy.model.dto;

/**
 * @author Peter Nikodem
 */
public class InvitationEmail implements Mailable {
    private final String to;
    private final String subject;
    private final String text;


    public InvitationEmail(String to, String voterId, String electionName, String uniqueElectionUrl) {
        this.to = to;
        this.subject = builtSubject(electionName);
        this.text = buildText(voterId, electionName, uniqueElectionUrl);
    }

    private String builtSubject(String electionName) {
        return electionName + " election invitation";
    }

    private String buildText(String voterId, String electionName, String uniqueElectionUrl) {
        return "Dear sir/madam,\n" +
                "you have been invited to participate in " + electionName + " election.\n" +
                "Please visit " + uniqueElectionUrl + " and express your preference.\n" +
                "Your unique voter id is : " + voterId + " . We kindly ask you to not share it with anyone.\n" +
                "Have a great day, \n" +
                "Nikodemocracy";
    }

    public String getTo() {
        return to;
    }

    public String getText(){
        return text;
    }

    public String getSubject(){
        return subject;
    }
}
