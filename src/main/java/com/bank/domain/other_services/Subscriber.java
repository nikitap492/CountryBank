package com.bank.domain.other_services;

import com.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Subscriber {

    @Id
    private String email;

    @JsonCreator
    public Subscriber(@JsonProperty("email") String email) {
        this.email = email;
    }

    public Subscriber(Account account) {
        this.email = account.getUser().getEmail();
    }
}
