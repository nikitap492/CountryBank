package com.cbank.domain;

import com.cbank.utils.RandomUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Data
@Entity
@Table(name = "accounts")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Account extends Persistable{

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
        this.num = RandomUtils.generateAccountNum();
        this.current = Boolean.TRUE;
    }

}
