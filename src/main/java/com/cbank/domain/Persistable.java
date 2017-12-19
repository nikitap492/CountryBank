package com.cbank.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Data
@MappedSuperclass
public class Persistable {

    @Id
    @GeneratedValue
    private Long id;
}
