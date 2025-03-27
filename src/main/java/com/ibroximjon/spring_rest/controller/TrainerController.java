package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.model.Training;
import com.ibroximjon.spring_rest.service.TrainerService;
import com.ibroximjon.spring_rest.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@Tag(name = "Trainer Management")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/register")
    @Operation(description = "Register a new trainer")
    public Trainer register(@RequestBody Trainer trainer) {
        return trainerService.registerTrainer(trainer);
    }

    @GetMapping("/{username}")
    @Operation(description = "Get trainer profile")
    public Trainer getProfile(@PathVariable String username) {
        return trainerService.getProfile(username);
    }

    @PutMapping("/{username}")
    @Operation(description = "Update trainer profile (specialization is read-only)")
    public Trainer updateProfile(@PathVariable String username, @RequestBody Trainer updated) {
        return trainerService.updateProfile(username, updated);
    }

    @PatchMapping("/{username}/status")
    @Operation(description = "Activate or deactivate trainer")
    public void changeStatus(@PathVariable String username, @RequestParam boolean isActive) {
        trainerService.setActiveStatus(username, isActive);
    }

    @GetMapping("/{username}/trainees")
    @Operation(description = "Get trainees assigned to trainer")
    public List<Trainee> getTrainees(@PathVariable String username) {
        return trainerService.getTrainees(username);
    }

    @GetMapping("/{username}/trainings")
    @Operation(description = "Get trainer's trainings with filters")
    public List<Training> getTrainings(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(required = false) String traineeName
    ) {
        return trainingService.getTrainerTrainings(username, from, to, traineeName);
    }
}
