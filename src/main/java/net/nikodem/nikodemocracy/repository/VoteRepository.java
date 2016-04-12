package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import net.nikodem.nikodemocracy.model.jpa.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Peter Nikodem
 */
public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    @Query("SELECT v.voteKey FROM Vote v where v.answer = :a")
    List<String> findVoteKeyByAnswerOrderByVoteKey(@Param("a") AnswerEntity a);
}
