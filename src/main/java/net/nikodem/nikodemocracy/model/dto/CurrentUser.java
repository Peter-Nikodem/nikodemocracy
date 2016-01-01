package net.nikodem.nikodemocracy.model.dto;

import net.nikodem.nikodemocracy.model.jpa.ElectionAdmin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Peter Nikodem
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private static final List<GrantedAuthority> LIST_OF_ELECTION_ADMIN_ROLES = buildAuthorityList(SecurityRole.ROLE_ELECTION_ADMIN);

    private final String email;

    public static CurrentUser createElectionAdmin(String username, String password, String email) {
        return new CurrentUser(username, password, email, LIST_OF_ELECTION_ADMIN_ROLES);
    }

    public static CurrentUser buildFromEntity(ElectionAdmin a) {
        return new CurrentUser(a.getUsername(),a.getPassword(),a.getEmail(),LIST_OF_ELECTION_ADMIN_ROLES);
    }

    private static List<GrantedAuthority> buildAuthorityList(SecurityRole... roles) {
        String[] userRoles =  Arrays.asList(roles).stream().map(SecurityRole::toString).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }

    private CurrentUser(String username, String password, String email, List<GrantedAuthority> roles) {
        super(username, password, roles);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CurrentUser that = (CurrentUser) o;

        return !(email != null ? !email.equals(that.email) : that.email != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
