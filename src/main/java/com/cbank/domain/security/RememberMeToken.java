package com.cbank.domain.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class RememberMeToken {

    @Id
    private String series;
    private String username;
    private String tokenValue;
    private Date date;

    public RememberMeToken(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public RememberMeToken(PersistentRememberMeToken token) {
        this(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    public PersistentRememberMeToken getPersistentToken() {
        return new PersistentRememberMeToken(username, series, tokenValue, date);
    }
}
