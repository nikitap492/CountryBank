package com.cbank.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Podshivalov N.A.
 * @since 20.11.2017.
 */
@Data
@Entity
@Table(name = "clients")
@EqualsAndHashCode(callSuper = false)
public class Client extends Persistable  {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userId;

    Client(String name, String address, String email, String userId) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.userId = userId;
    }
}
