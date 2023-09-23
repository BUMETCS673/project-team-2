package com.soloSavings.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.soloSavings.model.helper.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Integer transaction_id;
    private Integer user_id;
    private String source;
    private TransactionType transaction_type;
    private Double amount;
    private LocalDate transaction_date;
}

