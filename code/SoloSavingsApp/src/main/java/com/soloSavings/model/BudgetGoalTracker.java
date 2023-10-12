package com.soloSavings.model;

import com.soloSavings.model.helper.BudgetGoalType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="budgetgoaltracker")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetGoalTracker {

    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private BudgetGoalType budgetGoalType;
    private String source;
    private Double targetAmount;
    private Double actualAmount;
    private Integer userId;

}
