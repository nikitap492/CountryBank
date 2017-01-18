package com.bank.service;

public interface BlockerService {

    enum  UserFailStatus {
        WRONG, BLOCK
    }

    UserFailStatus failed(String loginOrEmail);
    void success(String loginOrEmail);
}
