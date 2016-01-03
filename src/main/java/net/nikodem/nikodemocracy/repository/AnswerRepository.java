package net.nikodem.nikodemocracy.repository;

import net.nikodem.nikodemocracy.model.jpa.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity,Long> {
}
