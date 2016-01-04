package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import net.nikodem.nikodemocracy.model.jpa.ElectionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Peter Nikodem
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity,Long> {
    List<AnswerEntity> findByElectionOrderByAnswerOrder(ElectionEntity election);

    Optional<AnswerEntity> findByAnswerTextAndElection(String answer, ElectionEntity electionEntity);
}
