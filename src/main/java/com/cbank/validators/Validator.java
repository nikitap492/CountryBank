package com.cbank.validators;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface Validator<T> {

    void validate(T t);

}
