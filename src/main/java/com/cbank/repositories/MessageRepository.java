package com.cbank.repositories;

import com.cbank.domain.message.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface MessageRepository extends CrudRepository<Message, Long>{
}
