package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.model.Trainee;

import java.util.List;

public interface TrainerService {
    Trainer registerTrainer(Trainer trainer);
    Trainer getProfile(String username);
    Trainer updateProfile(String username, Trainer updated);
    void setActiveStatus(String username, boolean isActive);
    List<Trainee> getTrainees(String trainerUsername);
}
