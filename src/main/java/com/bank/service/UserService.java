package com.bank.service;

import com.bank.domain.other.BaseToken;
import com.bank.domain.other.RegistrationToken;
import com.bank.domain.other.ResetPasswordToken;
import com.bank.domain.user.User;
import com.bank.domain.user.UserRegister;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * {@link com.bank.service.impl.PersistenceUserDetailService}
 */
public interface UserService extends UserDetailsService {

    void save(User... user);

    void save(UserRegister userRegister);

    User findByUsername(String username);

    boolean findByUsernameOrEmail(String usernameOrEmail);

    ResetPasswordToken createResetPasswordToken(String usernameOrEmail);

    RegistrationToken createRegistrationToken(User user);

    ResetPasswordToken findResetPasswordToken(String id);

    RegistrationToken findRegistrationToken(String token);

    int checkTokens();

    void resetPassword(ResetPasswordToken token, String password);

    void setEnabled(RegistrationToken token);

    void setNonValidityToken(BaseToken token);
}
