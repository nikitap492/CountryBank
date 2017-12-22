package com.cbank.services.impl.user;

import com.cbank.domain.user.User;
import com.cbank.repositories.UserRepository;
import com.cbank.services.TokenService;
import com.cbank.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Optional;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Slf4j
@Service
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
        return userRepository.findByUsername(username);
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
    @Transactional
    public void enable(String tokenId) {
        log.debug("#enable({})", tokenId);
        val token = tokenService.get(tokenId);

        byUsername(token.getUsername())
                .ifPresent(user -> {
                    user.setEnabled(true);
                    userRepository.save(user);
                });

    }

    @Override
    public void lock(User user) {
        log.debug("#lock({})", user);
        user.setNonLocked(false);
        save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("#loadUserByUsername({})", username);
        return byUsername(username)
                .map(user -> (UserDetails) user)
                .orElseThrow( () -> new UsernameNotFoundException("User : " + username + " was not found "));
    }
}
