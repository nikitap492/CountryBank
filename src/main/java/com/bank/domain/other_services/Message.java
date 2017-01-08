package com.bank.domain.other_services;

import com.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
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

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;
        return email.equals(message1.email) && name.equals(message1.name) && message.equals(message1.message);

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
