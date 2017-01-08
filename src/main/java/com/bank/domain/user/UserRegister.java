package com.bank.domain.user;

import com.bank.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Ajax user {@link User}
 */
public class UserRegister {

    private User user;
    private Account account;

    @JsonCreator
    public UserRegister(@JsonProperty("username") String username, @JsonProperty("password") String password,
                        @JsonProperty("email") String email, @JsonProperty("name") String name,
                        @JsonProperty("address") String address) {
        this.user = new User(username, password, email);
        this.account = new Account(user, name, address);

    }

    public UserRegister(User user, Account account) {
        this.user = user;
        this.account = account;
    }

    public User getUser() {
        return user;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "UserRegister{" +
                "account=" + account +
                ", user=" + user +
                '}';
    }


}
