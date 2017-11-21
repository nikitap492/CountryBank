package com.cbank.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Getter
@AllArgsConstructor
public enum  MessageTemplate {
    REGISTRATION_CONFIRMATION("You are welcomed to Country Bank!");

    public final String title;
}
