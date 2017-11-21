package com.cbank.controllers;


import com.cbank.domain.Client;
import com.cbank.domain.Subscriber;
import com.cbank.services.ClientService;
import com.cbank.services.SubscribeService;
import com.cbank.validators.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/subscribers")
public class SubscriberController {
    private SubscribeService subscribeService;
    private ClientService clientService;

    @ModelAttribute
    public Client client(Authentication authentication) {
        return Optional.ofNullable(authentication)
                    .flatMap(auth -> clientService.byUserId(auth.getName()))
                    .orElse(null);
    }

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

        return ValidationUtils.email(subscriber.getEmail())
                .getError()
                .map(error ->  ResponseEntity.badRequest().build())
                .orElseGet(() -> {
                            subscribeService.subscribe(toSave);
                            return ResponseEntity.ok().build();
                        });
    }

    /**
     * Method delete subscribed {@param account}
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<?> deleteSubscriber(@ModelAttribute Client client) {
        log.debug("Deleting subscriber by client: " + client);
        subscribeService.unsubscribe(Subscriber.of(client.getEmail()));
        return ResponseEntity.accepted().build();
    }

    /**
     * Method check whether {@param account} is subscribed
     *
     * @return {@link ResponseEntity<String>} status and message
     */
    @GetMapping
    public ResponseEntity<?> exists(@ModelAttribute Client client) {
        return subscribeService.byEmail(client.getEmail())
                .map((s) -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }

}
