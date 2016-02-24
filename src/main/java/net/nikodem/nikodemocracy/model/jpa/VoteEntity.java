package net.nikodem.nikodemocracy.model.jpa;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity(name ="Vote")
public class VoteEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String voteKey;

    @ManyToOne
    private AnswerEntity answer;

    public VoteEntity() {
    }

    public VoteEntity(String voteKey, AnswerEntity answer) {
        this.voteKey = voteKey;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "VoteEntity{" +
                "id=" + id +
                ", voteKey='" + voteKey + '\'' +
                ", answer=" + answer +
                '}';
    }
}
