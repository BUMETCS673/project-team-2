package com.soloSavings.model;

import com.soloSavings.model.helper.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private Integer transaction_id;
    private Integer user_id;
    private String source;
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;
    private Double amount;
    private LocalDate transaction_date;

    public Boolean isDebit(){
        return this.transaction_type.equals(TransactionType.DEBIT);
    }
    public Boolean isCredit(){
        return this.transaction_type.equals(TransactionType.CREDIT);
    }

}

