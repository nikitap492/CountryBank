package com.cbank.controllers;

import com.cbank.domain.RegistrationForm;
import com.cbank.services.ClientService;
import com.cbank.services.RegistrationService;
import com.cbank.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Poshivalov Nikita
 * @since 16.11.2016.
 */

@Slf4j
@RestController
@PreAuthorize("isAnonymous()")
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RegistrationService registrationService;
    private final ClientService clientService;


    @PostMapping(value = "/registration")
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationForm form) {
        log.debug("#addNewUser({})", form);
        return ResponseEntity.ok().body(registrationService.register(form));
    }

    @GetMapping(value = "/registration")
    public ResponseEntity<?> check(@RequestParam String usernameOrEmail) {
        return registrationService.check(usernameOrEmail)
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        if (request.password.length() < 4) {
            return ResponseEntity.badRequest().body(ErrorMessage.create("Password is too short, Please, type more strong password"));
        }
        userService.resetPassword(request.token, request.password);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/password")
    public ResponseEntity<?> forget(@RequestParam String loginOrEmail) {
        log.debug("#forget({})", loginOrEmail);
        clientService.accessRecovery(loginOrEmail);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/confirmation")
    public ResponseEntity<?> confirm(@RequestBody ConfirmationRequest request) {
        registrationService.confirm(request.token);
        return ResponseEntity.ok().build();
    }


    @ToString(exclude = "password")
    private static class ResetPasswordRequest {
        private String password;
        private String token;
    }

    @Data
    private static class ConfirmationRequest{
        private String token;
    }


}
