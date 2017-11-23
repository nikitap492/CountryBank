package com.cbank.services.impl.user;


import com.cbank.services.AuthenticationService;
import com.cbank.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Map<String, Integer> attemptsRepository = new ConcurrentHashMap<>();
    private final UserService userService;

    @Override
    public UserFailStatus failed(String login) {
        log.debug("#failed({})", login);
            return userService.byUsername(login)
                    .map(user -> {
                        int attempt = attempts(user.getUsername());
                        if (++attempt > MAX_ATTEMPTS) {
                            userService.lock(user);
                            return UserFailStatus.BLOCK;
                        } else {
                            attemptsRepository.put(login, attempt);
                            return UserFailStatus.WRONG;
                        }
                    }).orElse(UserFailStatus.WRONG);
    }


    private int attempts(String login){
        return Optional.ofNullable(attemptsRepository.get(login)).orElse(0);
    }


    @Override
    public void success(String login) {
        log.debug("#success({})", login);
        attemptsRepository.remove(login);
    }
}
