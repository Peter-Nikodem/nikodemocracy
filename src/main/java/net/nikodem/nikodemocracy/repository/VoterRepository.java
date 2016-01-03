package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.VoterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface VoterRepository extends JpaRepository<VoterEntity, Long> {
}
