package com.bank.domain.other;

import com.bank.domain.user.User;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class BaseToken {
    @Id
    private String token;

    @OneToOne
    private User user;

    private LocalDateTime tokenDateTime;

    private Boolean validity;

    public BaseToken() {
    }

    public BaseToken(User user) {
        this.user = user;
        this.validity = true;
        this.tokenDateTime = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTokenDateTime() {
        return tokenDateTime;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseToken token1 = (BaseToken) o;

        return token.equals(token1.token);

    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }


    @Override
    public String toString() {
        return "ResetPasswordToken{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", tokenDateTime=" + tokenDateTime +
                ", validity=" + validity +
                '}';
    }
}
