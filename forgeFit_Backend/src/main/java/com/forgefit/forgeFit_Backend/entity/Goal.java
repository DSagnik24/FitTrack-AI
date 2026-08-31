package com.forgefit.forgeFit_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false)
    private GoalType goalType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;

    @Column(name = "target_weight_kg")
    private BigDecimal targetWeightKg;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "target_calories")
    private Integer targetCalories;

    @Column(name = "target_protein_g")
    private Integer targetProteinG;

    @Column(name = "target_steps")
    private Integer targetSteps;

    @Column(name = "target_water_liters")
    private BigDecimal targetWaterLiters;
}