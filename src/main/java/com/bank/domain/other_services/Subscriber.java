package com.bank.domain.other_services;

import com.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Subscriber {

    @Id
    private String email;

    public Subscriber() {
    }

    @JsonCreator
    public Subscriber(@JsonProperty("email") String email) {
        this.email = email;
    }

    public Subscriber(Account account) {
        this.email = account.getUser().getEmail();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscriber that = (Subscriber) o;

        return email.equals(that.email);

    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "email='" + email + '\'' +
                '}';
    }
}
