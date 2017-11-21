package com.cbank.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Data
@RequiredArgsConstructor(staticName = "create")
public class ErrorMessage{
    private final String message;
    private LocalDateTime timestamps = LocalDateTime.now();
}