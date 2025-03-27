package com.ibroximjon.spring_rest.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "trainer")
public class Trainer extends User {
    @ManyToOne
    @JoinColumn(name = "training_type_id")
    private TrainingType specialization;

    @ManyToMany(mappedBy = "trainers")
    private List<Trainee> trainees;

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
}