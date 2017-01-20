package com.bank.service;

public interface BlockerService {
    int MAX_ATTEMPTS = 3;

    enum  UserFailStatus {
        WRONG, BLOCK
    }

    UserFailStatus failed(String loginOrEmail);
    void success(String loginOrEmail);
}
