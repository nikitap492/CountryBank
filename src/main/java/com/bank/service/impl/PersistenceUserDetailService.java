package com.bank.service.impl;

import com.bank.domain.other.BaseToken;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import com.bank.repositories.RegistrationTokenRepository;
import com.bank.repositories.ResetPasswordTokenRepository;
import com.bank.repositories.UserRepository;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Implementation of {@link UserService}
 * {@link UserService} is wrapper on {@link org.springframework.security.core.userdetails.UserDetailsService}
 */

@Service
public class PersistenceUserDetailService implements UserService {

    private static final Logger log = LoggerFactory.getLogger(PersistenceUserDetailService.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

    /**
     * Method tries to find user by name or email {@param nameOrEmail}
     *
     * @return user
     * @throws UsernameNotFoundException then user has not found
     */
    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        User login = repository.findOne(nameOrEmail);
        User user = login == null ? repository.findByEmail(nameOrEmail) : login;
        log.debug("Trying to find user  : " + user);
        if (user == null) {
            throw new UsernameNotFoundException("User : " + nameOrEmail + " was not found ");
        }
        return user;
    }

    /**
     * Method for saving users
     * There are encoder for encoding password in database
     */
    @Override
    @Transactional
    public void save(User... users) {
        for (User user : users) {
            User check = repository.findOne(user.getUsername());
            Assert.isNull(check, "User already exist");
            String hash = encoder.encode(user.getPassword());
            user.setPassword(hash);
            repository.save(user);
            log.debug("User : " + user.getUsername() + " has been saved");
        }
    }

    /**
     * Method for saving user by ajax entity {@link UserRegister}
     */
    @Override
    public void save(UserRegister userRegister) {
        save(userRegister.getUser());
        accountService.save(userRegister.getAccount());
    }

    @Override
    public User findByUsername(String username) {
        return repository.findOne(username);
    }

    @Override
    public boolean findByUsernameOrEmail(String usernameOrEmail) {
        return repository.findOne(usernameOrEmail) != null || repository.findByEmail(usernameOrEmail) != null;
    }

    @Override
    public ResetPasswordToken createResetPasswordToken(String emailOrUsername) {
        User user = (User) loadUserByUsername(emailOrUsername);
        ResetPasswordToken token = new ResetPasswordToken(user);
        resetPasswordTokenRepository.save(token);
        log.debug("Created token " + token);
        return token;
    }

    @Override
    public RegistrationToken createRegistrationToken(User user) {
        RegistrationToken token = new RegistrationToken(user);
        registrationTokenRepository.save(token);
        return token;
    }

    @Override
    public ResetPasswordToken findResetPasswordToken(String id) {
        return resetPasswordTokenRepository.findOne(id);
    }

    @Override
    public RegistrationToken findRegistrationToken(String token) {
        return registrationTokenRepository.findOne(token);
    }

    @Override
    @Scheduled(cron = "${schedule.cron}")
    @Transactional
    public int checkTokens() {
        log.debug("Schedule check for tokens");
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        int j = resetPasswordTokenRepository.checkNonValidity(time);
        int i = registrationTokenRepository.checkNonValidity(time);
        log.debug((i + j) + " tokens has been updated");
        return i + j;
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordToken token, String password) {
        log.debug("Attempt to reset password by token " + token.getToken());
        User user = token.getUser();
        String hash = encoder.encode(password);
        user.setPassword(hash);
        repository.save(user);
        setNonValidityToken(token);
    }

    @Override
    @Transactional
    public void setEnabled(RegistrationToken token) {
        User user = token.getUser();
        user.setEnabled(true);
        setNonValidityToken(token);
        repository.save(user);
        log.debug(user.getUsername() + " was confirmed by token " + token.getToken());
    }

    @Override
    public void setNonValidityToken(BaseToken token) {
        token.setValidity(false);
        if (token instanceof RegistrationToken) {
            RegistrationToken t = (RegistrationToken) token;
            registrationTokenRepository.save(t);
        } else {
            ResetPasswordToken t = (ResetPasswordToken) token;
            resetPasswordTokenRepository.save(t);
        }
    }


    @Override
    public void setLocked(User user) {
        log.debug("Blocking user "  + user.getUsername() );
        user.setNonLocked(false);
        repository.save(user);
    }

}
