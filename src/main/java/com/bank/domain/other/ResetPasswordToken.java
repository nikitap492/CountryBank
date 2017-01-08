package com.bank.domain.other;

import com.bank.domain.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ResetPasswordToken extends BaseToken {

    public ResetPasswordToken() {
        super();
    }

    public ResetPasswordToken(User user) {
        super(user);
    }
}
