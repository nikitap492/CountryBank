package com.cbank.domain.credit;

import com.cbank.domain.Persistable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@EqualsAndHashCode( callSuper = true)
@NoArgsConstructor
public class Credit extends Persistable{

    private Long accountId;
    private CreditType type;
    private BigDecimal initialAmount;
    private LocalDateTime openedAt = LocalDateTime.now();
    private LocalDateTime updateAt;
    private CreditState state;
    private LocalDateTime closedAt;
    private Integer numOfWithdraws;
    private BigDecimal monthlySum;

    public String getTypeString(){
        return type.getDirection().toString().toLowerCase();
    }

}
