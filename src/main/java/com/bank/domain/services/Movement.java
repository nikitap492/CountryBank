package com.bank.domain.services;

import com.bank.domain.Bill;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Movement {

    private static final String NO_MESSAGE = "NO MESSAGE";

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Bill bill;
    @Enumerated
    private Direction direction;
    private Double quantity;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(length = 4000)
    private String message;

    public Movement() {
    }

    public Movement(Bill bill, Direction direction, Double quantity) {
        this(bill, direction, quantity, NO_MESSAGE);
    }

    public Movement(Bill bill, Direction direction, Double quantity, String message) {
        this(bill, direction, quantity, new Date(), message);
    }

    public Movement(Bill bill, Direction direction, Double quantity, Date date, String message) {
        this.bill = bill;
        this.direction = direction;
        this.quantity = quantity;
        this.date = date;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Bill getBill() {
        return bill;
    }

    public Direction getDirection() {
        return direction;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movement movement = (Movement) o;

        return bill.equals(movement.bill) && direction == movement.direction && quantity.equals(movement.quantity) && date.equals(movement.date);

    }

    @Override
    public int hashCode() {
        int result = bill.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + quantity.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", bill=" + bill +
                ", direction=" + direction +
                ", quantity=" + quantity +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
