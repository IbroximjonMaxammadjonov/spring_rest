package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.model.Training;
import com.ibroximjon.spring_rest.model.TrainingType;
import com.ibroximjon.spring_rest.repository.TraineeRepository;
import com.ibroximjon.spring_rest.repository.TrainerRepository;
import com.ibroximjon.spring_rest.repository.TrainingRepository;
import com.ibroximjon.spring_rest.repository.TrainingTypeRepository;
import com.ibroximjon.spring_rest.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Override
    public void addTraining(String traineeUsername, String trainerUsername, Training training) {
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found"));

        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found"));

        // Associate the training with the trainer and trainee
        training.setTrainees(List.of(trainee));
        training.setTrainers(List.of(trainer));

        // Validate trainingType exists
        TrainingType type = training.getTrainingType();
        if (type != null) {
            TrainingType validType = trainingTypeRepository.findById(type.getId())
                    .orElseThrow(() -> new NoSuchElementException("Training type not found"));
            training.setTrainingType(validType);
        }

        trainingRepository.save(training);
    }

    @Override
    public List<Training> getTraineeTrainings(String username, Date from, Date to, String trainerName, String trainingTypeName) {
        List<Training> trainings = trainingRepository.findByTrainees_Username(username);

        return trainings.stream()
                .filter(t -> from == null || !t.getTrainingDate().before(from))
                .filter(t -> to == null || !t.getTrainingDate().after(to))
                .filter(t -> trainerName == null || t.getTrainers().stream()
                        .anyMatch(tr -> (tr.getFirstName() + " " + tr.getLastName()).toLowerCase().contains(trainerName.toLowerCase())))
                .filter(t -> trainingTypeName == null || (
                        t.getTrainingType() != null &&
                                t.getTrainingType().getTypeName().equalsIgnoreCase(trainingTypeName)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainerTrainings(String username, Date from, Date to, String traineeName) {
        List<Training> trainings = trainingRepository.findByTrainers_Username(username);

        return trainings.stream()
                .filter(t -> from == null || !t.getTrainingDate().before(from))
                .filter(t -> to == null || !t.getTrainingDate().after(to))
                .filter(t -> traineeName == null || t.getTrainees().stream()
                        .anyMatch(tr -> (tr.getFirstName() + " " + tr.getLastName()).toLowerCase().contains(traineeName.toLowerCase())))
                .collect(Collectors.toList());
    }
}
