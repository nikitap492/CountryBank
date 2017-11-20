package com.bank.domain;

import com.bank.domain.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    @OneToOne
    private User user;


    public Account(User user, String name, String address) {
        this.address = address;
        this.name = name;
        this.user = user;
    }
}
