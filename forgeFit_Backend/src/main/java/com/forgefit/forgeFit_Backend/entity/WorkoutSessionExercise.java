package com.forgefit.forgeFit_Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_session_exercises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_session_id", nullable = false)
    private WorkoutSession workoutSession;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "sets_completed")
    private Integer setsCompleted;

    @Column(name = "reps_completed")
    private Integer repsCompleted;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "duration_seconds")
    private Integer durationSeconds;

    private String notes;

    @Column(name = "exercise_order")
    private Integer exerciseOrder;
}