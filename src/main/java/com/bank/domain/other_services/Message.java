package com.bank.domain.other_services;

import com.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String name;
    private String message;

    @JsonCreator
    public Message(@JsonProperty("email") String email, @JsonProperty("name") String name,
                   @JsonProperty("message") String message) {
        this.email = email;
        this.name = name;
        this.message = message;
    }

    public Message(Account account, String message) {
        this.email = account.getUser().getEmail();
        this.name = account.getName();
        this.message = message;
    }
}
