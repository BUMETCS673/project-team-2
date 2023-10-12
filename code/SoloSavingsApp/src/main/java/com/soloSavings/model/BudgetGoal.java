package com.soloSavings.model;

import com.soloSavings.model.helper.BudgetGoalType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="budgetgoals")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private BudgetGoalType budgetGoalType;
    private String source;
    private Double targetAmount;
    private LocalDate startDate;
}
