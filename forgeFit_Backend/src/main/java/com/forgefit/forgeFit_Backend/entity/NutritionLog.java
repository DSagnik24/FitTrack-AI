package com.forgefit.forgeFit_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "nutrition_logs",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "date"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private Integer calories;

    @Column(name = "protein_g")
    private BigDecimal proteinG;

    @Column(name = "carbohydrates_g")
    private BigDecimal carbohydratesG;

    @Column(name = "fats_g")
    private BigDecimal fatsG;

    @Column(name = "fiber_g")
    private BigDecimal fiberG;

    @Column(name = "water_liters")
    private BigDecimal waterLiters;

    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}