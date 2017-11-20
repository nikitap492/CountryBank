package com.bank.domain.other;

import com.bank.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter
@ToString
public class BaseToken {
    @Id
    private String token;

    @OneToOne
    private User user;
    private LocalDateTime tokenDateTime;
    @Setter
    private Boolean validity;

    BaseToken(User user) {
        this.user = user;
        this.validity = true;
        this.tokenDateTime = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    BaseToken(User user, LocalDateTime tokenDateTime) {
        this.user = user;
        this.tokenDateTime = tokenDateTime;
        this.token = UUID.randomUUID().toString();
        this.validity = true;
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
