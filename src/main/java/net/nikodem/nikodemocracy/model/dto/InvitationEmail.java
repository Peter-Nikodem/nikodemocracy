package net.nikodem.nikodemocracy.model.dto;

/**
 * @author Peter Nikodem
 */
public class InvitationEmail {
    private final String to;
    private final String subject;
    private final String text;


    public InvitationEmail(String to, String voterId, String electionName, String uniqueElectionUrl) {
        this.to = to;
        this.subject = electionName + " election invitation";
        this.text = buildText(voterId, electionName, uniqueElectionUrl);

    }

    private String buildText(String voterId, String electionName, String uniqueElectionUrl) {
        return new StringBuilder()
                .append("Dear sir/madam,\n")
                .append("you have been invited to participate in ").append(electionName).append( " election.\n")
                .append("Please visit ").append(uniqueElectionUrl).append(" and express your preference.\n")
                .append("Your unique voter id is : ").append(voterId).append(" . We kindly ask you to not share it with anyone.\n")
                .append("Have a great day, \n")
                .append("Nikodemocracy").toString();
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
