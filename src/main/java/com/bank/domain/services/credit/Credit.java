package com.bank.domain.services.credit;

import com.bank.domain.Bill;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDate;

import static com.bank.domain.services.credit.CreditState.CLOSED;
import static com.bank.domain.services.credit.CreditState.OPENED;
import static java.time.LocalDate.now;


@Data
@Entity
@EqualsAndHashCode(exclude = "id")
public class Credit {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Bill bill;
    private CreditFrequency frequency;
    private CreditType type;
    private Double money;
    private Date beginDate;
    private Date lastUpdateDate;
    private CreditState state;
    private Date endDate;
    private Integer numOfWithdraws;

    public Credit() {
    }

    private Credit(Long id, Bill bill, CreditFrequency frequency, CreditType type, Double money, Date beginDate, Date lastUpdateDate,
                   CreditState state, Date endDate, Integer numOfWithdraws) {
        this.bill = bill;
        this.frequency = frequency;
        this.type = type;
        this.money = money;
        this.beginDate = beginDate;
        this.lastUpdateDate = lastUpdateDate;
        this.state = state;
        this.endDate = endDate;
        this.numOfWithdraws = numOfWithdraws;
        this.id = id;
    }


    public Credit setLastUpdateDate() {
        Date date = convertDate(now());
        if (date.compareTo(this.endDate) >= 0) {
            return new Credit(id, bill, frequency, type, money, beginDate, date, CLOSED, endDate, numOfWithdraws);
        }
        return new Credit(id, bill, frequency, type, money, beginDate, date, state, endDate, numOfWithdraws);
    }

    private static Date convertDate(LocalDate date) {
        return Date.valueOf(date);
    }

    /**
     * Builder for {@link Credit}
     */
    public static class CreditBuilder {

        public static Credit of(Bill bill, CreditFrequency frequency, CreditType type, Double money, LocalDate beginDate, LocalDate lastUpdateDate,
                                CreditState state, LocalDate endDate, Integer numOfWithdraws) {
            return new Credit(null, bill, frequency, type, money, convertDate(beginDate),
                    convertDate(lastUpdateDate), state, convertDate(endDate), numOfWithdraws);
        }

        public static Credit of(Bill bill, CreditFrequency frequency, CreditType type, Double money, LocalDate endDate, Integer numOfWithdraws) {
            return of(bill, frequency, type, money, now(), now(), OPENED, endDate, numOfWithdraws);
        }

        public static Credit of(Bill bill, CreditFrequency frequency, CreditType type, Double money, int numOfWithdraws) {
            LocalDate endDate = null;
            switch (frequency) {
                case MONTH:
                    endDate = now().plusMonths(numOfWithdraws);
                    break;
                case WEAK:
                    endDate = now().plusWeeks(numOfWithdraws);
                    break;
            }
            return of(bill, frequency, type, money, endDate, numOfWithdraws);
        }
    }
}
