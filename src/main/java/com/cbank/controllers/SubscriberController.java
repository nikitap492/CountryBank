package com.cbank.controllers;


import com.cbank.domain.Client;
import com.cbank.domain.Subscriber;
import com.cbank.services.SubscribeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@AggregationModel
@RequestMapping("/subscribers")
public class SubscriberController {
    private SubscribeService subscribeService;

    /**
     * Saving new anonymous subscribed or  {@param account}
     */
    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody Subscriber subscriber,
                                       @ModelAttribute Client client) {

        val toSave = Optional.ofNullable(client)
                .map(Client::getEmail)
                .map(Subscriber::of)
                .orElse(subscriber);

        return ResponseEntity.ok(subscribeService.subscribe(toSave));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<?> deleteSubscriber(@ModelAttribute Client client) {
        log.debug("Deleting subscriber by client: " + client);
        subscribeService.unsubscribe(client.getEmail());
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<?> exists(@ModelAttribute Client client) {
        return Optional.ofNullable(client)
                .flatMap((c) -> subscribeService.byEmail(c.getEmail()))
                .map((s) -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
