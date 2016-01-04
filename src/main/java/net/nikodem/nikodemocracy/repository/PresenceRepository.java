package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.model.jpa.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface PresenceRepository extends JpaRepository<PresenceEntity,Long> {
    Optional<PresenceEntity> findByVoterKeyAndElection(String voterKey, ElectionEntity electionEntity);
}
