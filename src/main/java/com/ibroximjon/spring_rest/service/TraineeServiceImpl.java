package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.repository.TraineeRepository;
import com.ibroximjon.spring_rest.repository.TrainerRepository;
import com.ibroximjon.spring_rest.service.TraineeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public Trainee registerTrainee(Trainee trainee) {
        // Generate username and password
        String username = generateUsername(trainee.getFirstName(), trainee.getLastName());
        String password = generatePassword();

        trainee.setUsername(username);
        trainee.setPassword(password);
        trainee.setActive(true);

        return traineeRepository.save(trainee);
    }

    @Override
    public Trainee getProfile(String username) {
        return traineeRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Trainee not found"));
    }

    @Override
    public Trainee updateProfile(String username, Trainee updated) {
        Trainee existing = getProfile(username);

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setAddress(updated.getAddress());
        existing.setActive(updated.isActive());

        return traineeRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteProfile(String username) {
        if (!traineeRepository.existsByUsername(username)) {
            throw new NoSuchElementException("Trainee not found");
        }
        traineeRepository.deleteByUsername(username);
    }

    @Override
    public List<Trainer> getNotAssignedActiveTrainers(String username) {
        Trainee trainee = getProfile(username);
        Set<Integer> assignedIds = trainee.getTrainers().stream()
                .map(Trainer::getId)
                .collect(Collectors.toSet());

        return trainerRepository.findByIsActiveTrue().stream()
                .filter(trainer -> !assignedIds.contains(trainer.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Trainer> updateTrainerList(String traineeUsername, List<String> trainerUsernames) {
        Trainee trainee = getProfile(traineeUsername);

        List<Trainer> trainers = trainerRepository.findAll().stream()
                .filter(trainer -> trainerUsernames.contains(trainer.getUsername()))
                .collect(Collectors.toList());

        trainee.setTrainers(trainers);
        return traineeRepository.save(trainee).getTrainers();
    }

    @Override
    public void setActiveStatus(String username, boolean isActive) {
        Trainee trainee = getProfile(username);
        trainee.setActive(isActive);
        traineeRepository.save(trainee);
    }

    // Helper methods

    private String generateUsername(String firstName, String lastName) {
        return (firstName.charAt(0) + lastName + new Random().nextInt(1000)).toLowerCase();
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
