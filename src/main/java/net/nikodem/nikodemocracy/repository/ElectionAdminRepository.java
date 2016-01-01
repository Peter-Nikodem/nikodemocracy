package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.ElectionAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface ElectionAdminRepository extends JpaRepository<ElectionAdmin,String> {
    Optional<ElectionAdmin> findByUsername(String username);
}
