package com.bank.domain.services.credit;

import com.bank.domain.Bill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDate;

import static com.bank.domain.services.credit.CreditState.CLOSED;
import static com.bank.domain.services.credit.CreditState.OPENED;
import static java.time.LocalDate.now;


@Entity
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

    public Bill getBill() {
        return bill;
    }

    public CreditFrequency getFrequency() {
        return frequency;
    }

    public CreditType getType() {
        return type;
    }

    public Double getMoney() {
        return money;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public CreditState getState() {
        return state;
    }

    private static Date convertDate(LocalDate date) {
        return Date.valueOf(date);
    }

    public Integer getNumOfWithdraws() {
        return numOfWithdraws;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credit credit = (Credit) o;

        if (!bill.equals(credit.bill)) return false;
        if (frequency != credit.frequency) return false;
        if (type != credit.type) return false;
        if (!money.equals(credit.money)) return false;
        if (!beginDate.equals(credit.beginDate)) return false;
        if (!lastUpdateDate.equals(credit.lastUpdateDate)) return false;
        if (state != credit.state) return false;
        if (!endDate.equals(credit.endDate)) return false;
        return numOfWithdraws.equals(credit.numOfWithdraws);

    }

    @Override
    public int hashCode() {
        int result = bill.hashCode();
        result = 31 * result + frequency.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + money.hashCode();
        result = 31 * result + beginDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + numOfWithdraws.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", bill=" + bill +
                ", frequency=" + frequency +
                ", type=" + type +
                ", money=" + money +
                ", beginDate=" + beginDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", state=" + state +
                ", endDate=" + endDate +
                ", numOfWithdraws=" + numOfWithdraws +
                '}';
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
