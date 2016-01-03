package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.model.jpa.VoterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Peter Nikodem
 */
public interface VoterRepository extends JpaRepository<VoterEntity, Long> {
    List<VoterEntity> findByElection(ElectionEntity electionEntity);
}
