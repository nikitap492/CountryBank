package com.bank.domain.services;

import com.bank.domain.Bill;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = "id")
@ToString
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
}
