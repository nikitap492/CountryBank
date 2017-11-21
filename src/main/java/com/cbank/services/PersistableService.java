package com.cbank.services;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
public interface PersistableService<T> {
    T save (T t);
}
