package com.bank.domain.security;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class RememberMeToken {

    @Id
    private String series;
    private String username;
    private String tokenValue;
    private Date date;

    public RememberMeToken() {
    }

    public RememberMeToken(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    public RememberMeToken(PersistentRememberMeToken token) {
        this(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    public String getUsername() {
        return username;
    }

    public String getSeries() {
        return series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public PersistentRememberMeToken getPersistentToken() {
        return new PersistentRememberMeToken(username, series, tokenValue, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RememberMeToken token = (RememberMeToken) o;

        if (!series.equals(token.series)) return false;
        if (!username.equals(token.username)) return false;
        if (!tokenValue.equals(token.tokenValue)) return false;
        return date.equals(token.date);

    }

    @Override
    public int hashCode() {
        int result = series.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + tokenValue.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
