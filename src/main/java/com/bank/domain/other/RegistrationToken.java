package com.bank.domain.other;


import com.bank.domain.user.User;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class RegistrationToken extends BaseToken {

    public RegistrationToken() {
        super();
    }

    public RegistrationToken(User user) {
        super(user);
    }

    public RegistrationToken(User user, LocalDateTime time) {
        super(user, time);
    }
}
