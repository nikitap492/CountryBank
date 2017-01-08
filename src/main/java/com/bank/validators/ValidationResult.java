package com.bank.validators;

/**
 * Common validation entity what encapsulate either answer of validation or error
 */
public class ValidationResult<T> {
    private T entity;
    private String error;

    // getters and setters
    public T getEntity() {
        return entity;
    }

    public String getError() {
        return error;
    }

    void setEntity(T entity) {
        this.entity = entity;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return whether validation has error
     */
    public boolean hasError() {
        return error != null;
    }
}