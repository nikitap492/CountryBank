package com.bank.domain.services.credit;

import com.bank.domain.services.Direction;

import static com.bank.domain.services.Direction.IN;
import static com.bank.domain.services.Direction.OUT;


public enum CreditType {
    CREDIT_FOR_PERSONAL(9, IN, 0), CREDIT_FOR_BUSINESS(15, IN, 1), DEPOSIT_FOR_BUSINESS(4, OUT, 2);

    private final int per;
    private final Direction direction;
    private final int type;

    CreditType(int per, Direction direction, int type) {
        this.per = per;
        this.direction = direction;
        this.type = type;
    }

    public int getPer() {
        return per;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "CreditType{" +
                "per=" + per +
                ", direction=" + direction +
                '}';
    }

    public static CreditType getType(Integer i) {
        if (i < 0 || i > 2) throw new IllegalArgumentException("Wrong credit type");
        return values()[i];
    }

    public int getType() {
        return type;
    }
}
