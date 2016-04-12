package net.nikodem.nikodemocracy.model.jpa;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Presence")
public class PresenceEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String voterKey;

    @Column
    private boolean hasVoted;

    @ManyToOne
    private ElectionEntity election;

    public PresenceEntity(){
    }

    public PresenceEntity(String voterKey, ElectionEntity election) {
        this.voterKey = voterKey;
        this.election = election;
        this.hasVoted = false;
    }

    public String getVoterKey() {
        return voterKey;
    }

    public ElectionEntity getElection() {
        return election;
    }

    //TODO change name to something that's related to the presence, not voter. AlreadyAttended?
    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @Override
    public String toString() {
        return "PresenceEntity{" +
                "id=" + id +
                ", voterKey='" + voterKey + '\'' +
                ", hasVoted=" + hasVoted +
                ", election=" + election +
                '}';
    }
}
