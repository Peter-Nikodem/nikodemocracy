package net.nikodem.nikodemocracy.model.jpa;

import net.nikodem.nikodemocracy.model.dto.Election;

import javax.persistence.*;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Election")
public class ElectionEntity {

    @Id
    @GeneratedValue
    private long electionId;

    @Column
    private String name;

    @Column
    private String question;

    @Column
    private String shortUrl;

    @Column
    private boolean finished;

    @ManyToOne
    private AdminEntity admin;

    public ElectionEntity() {
    }

    public ElectionEntity(String name, String question, String shortUrl, AdminEntity admin) {
        this.name = name;
        this.question = question;
        this.shortUrl = shortUrl;
        this.admin = admin;
        this.finished = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }

    public static ElectionEntity buildFromDto(Election election) {
        return new ElectionEntity(
                election.getName(),
                election.getQuestion(),
                election.getShortUrl(),
                AdminEntity.buildFromDto(election.getAdmin()));
    }
}
