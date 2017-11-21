package com.cbank.repositories;

import com.cbank.domain.message.Feedback;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
