package com.bank.domain.other;


import com.bank.domain.user.User;

import javax.persistence.Entity;

@Entity
public class RegistrationToken extends BaseToken {

    public RegistrationToken() {
        super();
    }

    public RegistrationToken(User user) {
        super(user);
    }
}
