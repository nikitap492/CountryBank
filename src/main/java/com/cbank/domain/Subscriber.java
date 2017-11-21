package com.cbank.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "subscribers")
@Data
@NoArgsConstructor
public class Subscriber {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String email;

    public static Subscriber of(String email){
        val subscriber = new Subscriber();
        subscriber.setEmail(email);
        return subscriber;
    }
}
