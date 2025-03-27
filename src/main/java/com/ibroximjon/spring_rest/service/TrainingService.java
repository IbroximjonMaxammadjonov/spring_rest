package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Training;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    void addTraining(String traineeUsername, String trainerUsername, Training training);
    List<Training> getTraineeTrainings(String username, Date from, Date to, String trainerName, String trainingType);
    List<Training> getTrainerTrainings(String username, Date from, Date to, String traineeName);
}
