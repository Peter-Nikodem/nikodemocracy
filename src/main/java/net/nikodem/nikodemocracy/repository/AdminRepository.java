package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface AdminRepository extends JpaRepository<AdminEntity,String> {
    Optional<AdminEntity> findByUsername(String username);
}
