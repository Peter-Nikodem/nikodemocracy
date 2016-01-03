package net.nikodem.nikodemocracy.model.jpa;

import net.nikodem.nikodemocracy.model.dto.Admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author Peter Nikodem
 */
@Entity(name = "Admin")
public class AdminEntity {

    @Id
    @Column
    private String username;

    @NotNull
    @Column
    private String email;

    @NotNull
    @Column
    private String password;

    public AdminEntity() {
    }

    public AdminEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static AdminEntity buildFromDto(Admin admin) {
        return new AdminEntity(admin.getUsername(), admin.getEmail(), admin.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminEntity that = (AdminEntity) o;

        if (!username.equals(that.username)) return false;
        if (!email.equals(that.email)) return false;
        return password.equals(that.password);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AdminEntity{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
