package net.nikodem.nikodemocracy.model.jpa;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Voter")
public class VoterEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String voterKey;

    @Column
    private String email;

    @ManyToOne
    private ElectionEntity election;

    public VoterEntity(){
    }

    public VoterEntity(String email, String voterKey, ElectionEntity election) {
        this.email = email;
        this.voterKey = voterKey;
        this.election = election;
    }

    public String getVoterKey() {
        return voterKey;
    }

    public String getEmail() {
        return email;
    }

    public ElectionEntity getElection() {
        return election;
    }
}
