package com.bank.domain.other;

import com.bank.domain.user.User;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ResetPasswordToken extends BaseToken {

    public ResetPasswordToken(User user) {
        super(user);
    }

    public ResetPasswordToken(User user, LocalDateTime time) {
        super(user, time);
    }
}
