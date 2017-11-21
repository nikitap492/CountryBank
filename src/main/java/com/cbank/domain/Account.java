package com.cbank.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Random;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Data
@Entity
@Table(name = "accounts")
@EqualsAndHashCode(callSuper = false)
public class Account extends Persistable{
    private static final Random random = new Random();

    @Column(nullable = false)
    private Long clientId;

    @Column(unique = true, length = 16)
    private String num;

    /**
     * If this flag is true, all transactions expense money form current bill for account
     */
    private Boolean current = Boolean.TRUE;

    public Account(Long clientId) {
        this.clientId = clientId;
        this.num = generateAccountNum();
        this.current = Boolean.TRUE;
    }

    private String generateAccountNum(){
        return "" + ((long) (random.nextDouble() * 10000000000000000L));
    }
}
