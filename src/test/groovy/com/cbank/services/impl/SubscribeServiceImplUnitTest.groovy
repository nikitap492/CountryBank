package com.cbank.services.impl

import com.cbank.domain.Subscriber
import com.cbank.repositories.SubscriberRepository
import spock.lang.Specification

/**
 * @author Podshivalov N.A. 
 * @since 22.12.2017.
 */
class SubscribeServiceImplUnitTest extends Specification {

    def subscriberRepository = Mock(SubscriberRepository)
    def subscribeService = new SubscribeServiceImpl(subscriberRepository)

    def "Subscribe"() {
        given:
        def subscriber = Subscriber.of("smith65@mail.com")
        subscriberRepository.findByEmail(subscriber.email) >> Optional.empty()
        when: subscribeService.subscribe(subscriber)
        then: 1 * subscriberRepository.save(subscriber)
    }

    def "ByEmail"() {
        given:
        def subscriber = Subscriber.of("smith65@mail.com")
        subscriberRepository.findByEmail(subscriber.email) >> Optional.of(subscriber)
        when: def byEmail = subscribeService.byEmail(subscriber.email)
        then: byEmail.get() == subscriber
    }

    def "Unsubscribe"() {
        when: subscribeService.unsubscribe("smith65@mail.com")
        then: 1 * subscriberRepository.deleteByEmail("smith65@mail.com")
    }
}
