package com.bank.domain;

import javax.persistence.*;
import java.util.UUID;


@Entity
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
    private Boolean isCurrent;

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
        this.isCurrent = isCurrent;
    }


    //for JPA
    public Bill() {
    }

    //for JSON
    public Bill(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Double getMoney() {
        return money;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Boolean getCurrent() {
        return isCurrent;
    }

    public void setCurrent(Boolean current) {
        this.isCurrent = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return uuid.equals(bill.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", account=" + account +
                ", uuid=" + uuid +
                ", money=" + money +
                '}';
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
