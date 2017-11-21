package com.cbank.services.impl.user;

import com.cbank.domain.user.User;
import com.cbank.domain.security.BaseToken;
import com.cbank.repositories.UserRepository;
import com.cbank.services.TokenService;
import com.cbank.services.UserService;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> byUsername(String username) {
        return null;
    }

    @Override
    @Transactional
    public void resetPassword(String tokenId, String password) {
        val token = tokenService.get(tokenId);

       byUsername(token.getUsername())
                .map(user -> userRepository.save(setPassword(user, password)))
                .orElseThrow(EntityExistsException::new);
    }

    @Override
    public void passwordToken() {

    }

    @Override
    @Transactional
    public void enable(String tokenId) {
        val token = tokenService.get(tokenId);

        byUsername(token.getUsername())
                .ifPresent(user -> {
                    user.setEnabled(true);
                    userRepository.save(user);
                });

        log.debug(token.getUsername() + " was confirmed by token " + token.getToken());
    }

    @Override
    public void lock(BaseToken token) {
        val username = token.getUsername();
        log.debug("Blocking user "  + username );
        byUsername(username).ifPresent(user -> {
            user.setNonLocked(false);
            userRepository.save(user);
        });
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Trying to find user  : " + username);
        return byUsername(username)
                .map(user -> (UserDetails) user)
                .orElseThrow( () -> new UsernameNotFoundException("User : " + username + " was not found "));
    }
}
