package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import net.nikodem.nikodemocracy.model.jpa.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface PresenceRepository extends JpaRepository<PresenceEntity,Long> {
    Optional<PresenceEntity> findByVoterKeyAndElection(String voterKey, ElectionEntity electionEntity);

    @Query("SELECT count(p)>0 FROM Presence p WHERE p.hasVoted is false AND p.election.name = :electionName AND p.election.admin.username = :adminName")
    boolean unvotedPresenceExists(@Param("electionName") String electionName, @Param("adminName") String electionAdminUserName);
}
