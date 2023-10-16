package com.soloSavings.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue
    private Integer id;
    private String content;
    private Integer user_id;

    private Integer transaction_id;
}
