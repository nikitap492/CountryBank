package com.bank.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@ToString
public class Bill {

    private static final Double ZERO = 0.0;

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account account;

    /**
     * This column substitute of number of bank card (XXXX XXXX XXXX XXXX)
     */
    @Column(unique = true)
    private UUID uuid;
    private Double money;
    /**
     * If this flag is true, all transactions expense money form current bill for account
     */
    private Boolean current;

    public Bill(Account account) {
        this(account, ZERO);
    }

    public Bill(Account account, Double money) {
        this(account, UUID.randomUUID(), money, false);
    }

    public Bill(Account account, UUID uuid, Double money) {
        this(account, uuid, money, false);
    }

    public Bill(Account account, UUID uuid, Double money, Boolean isCurrent) {
        this(null, account, uuid, money, isCurrent);
    }

    public Bill(Long id, Account account, UUID uuid, Double money, Boolean isCurrent) {
        this.id = id;
        this.account = account;
        this.uuid = uuid;
        this.money = money;
        this.current = isCurrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return uuid.equals(bill.uuid);

    }

    @Override
    //todo to BigDecimal
    public int hashCode() {
        return uuid.hashCode();
    }

    public void income(Double d) {
        this.money = round(money + d);
    }

    public void expense(Double d) {
        this.money = round(money - d);
    }


    public static double round(Double n) {
        return (double) ((long) Math.round(n * 100)) / 100;
    }

}
