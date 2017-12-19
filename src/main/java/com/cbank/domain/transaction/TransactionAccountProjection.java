package com.cbank.domain.transaction;

import lombok.Data;
import lombok.val;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.cbank.domain.transaction.TransactionDirection.IN;
import static com.cbank.domain.transaction.TransactionDirection.OUT;

/**
 * @author Podshivalov N.A.
 * @since 19.12.2017.
 */
@Data
public class TransactionAccountProjection {


    /**
     * Another participant of transaction
     */
    private String accountNum;
    private TransactionDirection direction;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String details;

    public static TransactionAccountProjection from(Transaction transaction, String accountNum){
        val projection = new TransactionAccountProjection();
        projection.setDetails(transaction.getDetails());
        projection.setCreatedAt(transaction.getCreatedAt());
        projection.setAmount(transaction.getAmount());
        val isPayer = accountNum.equals(transaction.getPayer());

        if(isPayer){
            projection.setAccountNum(transaction.getRecipient());
            projection.setDirection(OUT);
        }else {
            projection.setAccountNum(transaction.getPayer());
            projection.setDirection(IN);
        }

        return projection;
    }

}
