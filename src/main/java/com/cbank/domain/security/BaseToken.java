package com.cbank.domain.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "base_token")
public class BaseToken {
    @Id
    private String token = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime createdAt  = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean valid = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseTokenType tokenType;


    public static BaseToken of(String username, BaseTokenType tokenType){
        val token = new BaseToken();
        token.username = username;
        token.tokenType = tokenType;
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseToken token1 = (BaseToken) o;
        return token.equals(token1.token);

    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
