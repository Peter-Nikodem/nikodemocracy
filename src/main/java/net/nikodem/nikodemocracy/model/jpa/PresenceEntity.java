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

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}