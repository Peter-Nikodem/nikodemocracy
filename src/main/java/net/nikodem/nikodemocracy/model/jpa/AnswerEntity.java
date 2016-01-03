package net.nikodem.nikodemocracy.model.jpa;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Answer")
public class AnswerEntity {

    @Id
    @GeneratedValue
    private long answerId;

    @Column
    private int answerOrder;

    @Column
    private String answerText;

    @ManyToOne
    private ElectionEntity election;

    public AnswerEntity(){
    }

    public AnswerEntity(String answer, int answerOrder,ElectionEntity election) {
        this.answerOrder = answerOrder;
        this.answerText = answer;
        this.election = election;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public String getAnswerText() {
        return answerText;
    }

    public ElectionEntity getElection() {
        return election;
    }

    @Override
    public String toString() {
        return "AnswerEntity{" +
                "answerId=" + answerId +
                ", answerOrder=" + answerOrder +
                ", answerText='" + answerText + '\'' +
                ", election=" + election +
                '}';
    }
}
