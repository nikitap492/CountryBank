package com.bank.repositories;

import com.bank.domain.other_services.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

}
