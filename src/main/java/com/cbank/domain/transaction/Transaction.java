package com.cbank.domain.transaction;

import com.cbank.domain.Persistable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends Persistable {

    @Column(nullable = false)
    private String payer;

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private BigDecimal amount;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private String details;
}
