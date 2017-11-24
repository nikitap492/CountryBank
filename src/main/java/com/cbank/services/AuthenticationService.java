package com.cbank.services;

public interface AuthenticationService {
    int MAX_ATTEMPTS = 3;

    enum  UserFailStatus {
        WRONG, BLOCK
    }

    UserFailStatus failed(String login);

    void success(String login);
}
