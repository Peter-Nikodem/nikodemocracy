package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {

}
