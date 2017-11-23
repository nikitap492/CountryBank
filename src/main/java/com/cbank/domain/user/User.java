package com.cbank.domain.user;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

/**
 * Wrapper of UserDetails
 */

@Entity
@Table(name = "users")
@Data
@ToString(exclude = {"password", "salt"})
public class User implements UserDetails {
    private static final StringKeyGenerator keyGenerator = KeyGenerators.string();

    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean enabled = false;
    private boolean nonLocked = true;

    @Column(name = "salt", nullable = false, updatable = false)
    private String salt = keyGenerator.generateKey();

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

}