package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;

import java.util.List;

public interface TraineeService {
    Trainee registerTrainee(Trainee trainee);
    Trainee getProfile(String username);
    Trainee updateProfile(String username, Trainee updated);
    void deleteProfile(String username);
    List<Trainer> getNotAssignedActiveTrainers(String username);
    List<Trainer> updateTrainerList(String traineeUsername, List<String> trainerUsernames);
    void setActiveStatus(String username, boolean isActive);
}
