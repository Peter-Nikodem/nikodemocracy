package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface ElectionRepository extends JpaRepository<ElectionEntity,Long>{
    Optional<ElectionEntity> findByNameAndAdminUsername(String name, String username);
}
