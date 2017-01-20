package com.bank.domain.other;

import com.bank.domain.user.User;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ResetPasswordToken extends BaseToken {

    public ResetPasswordToken() {
        super();
    }

    public ResetPasswordToken(User user) {
        super(user);
    }

    public ResetPasswordToken(User user, LocalDateTime time) {
        super(user, time);
    }
}
