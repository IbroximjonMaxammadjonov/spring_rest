package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.repository.TraineeRepository;
import com.ibroximjon.spring_rest.repository.TrainerRepository;
import com.ibroximjon.spring_rest.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public Trainer registerTrainer(Trainer trainer) {
        // Generate username and password
        String username = generateUsername(trainer.getFirstName(), trainer.getLastName());
        String password = generatePassword();

        trainer.setUsername(username);
        trainer.setPassword(password);
        trainer.setActive(true);

        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer getProfile(String username) {
        return trainerRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Trainer not found"));
    }

    @Override
    public Trainer updateProfile(String username, Trainer updated) {
        Trainer existing = getProfile(username);

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setActive(updated.isActive());

        // ⚠️ Do not change specialization (as per task it's read-only)

        return trainerRepository.save(existing);
    }

    @Override
    public void setActiveStatus(String username, boolean isActive) {
        Trainer trainer = getProfile(username);
        trainer.setActive(isActive);
        trainerRepository.save(trainer);
    }

    @Override
    public List<Trainee> getTrainees(String trainerUsername) {
        Trainer trainer = getProfile(trainerUsername);
        return trainer.getTrainees();
    }

    // --- Helper Methods ---

    private String generateUsername(String firstName, String lastName) {
        return (firstName.charAt(0) + lastName + new Random().nextInt(1000)).toLowerCase();
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
