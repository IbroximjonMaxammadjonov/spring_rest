package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.model.Trainer;
import com.ibroximjon.spring_rest.model.Training;
import com.ibroximjon.spring_rest.service.TraineeService;
import com.ibroximjon.spring_rest.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/trainees")
@Tag(name = "Trainee Management")
public class TraineeController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/register")
    @Operation(description = "Register new trainee")
    public Trainee register(@RequestBody Trainee trainee) {
        return traineeService.registerTrainee(trainee);
    }

    @GetMapping("/{username}")
    @Operation(description = "Get trainee profile")
    public Trainee getProfile(@PathVariable String username) {
        return traineeService.getProfile(username);
    }

    @PutMapping("/{username}")
    @Operation(description = "Update trainee profile")
    public Trainee updateProfile(@PathVariable String username, @RequestBody Trainee updated) {
        return traineeService.updateProfile(username, updated);
    }

    @DeleteMapping("/{username}")
    @Operation(description = "Delete trainee profile")
    public void delete(@PathVariable String username) {
        traineeService.deleteProfile(username);
    }

    @PatchMapping("/{username}/status")
    @Operation(description = "Activate or deactivate trainee")
    public void changeStatus(@PathVariable String username, @RequestParam boolean isActive) {
        traineeService.setActiveStatus(username, isActive);
    }

    @GetMapping("/{username}/trainers/unassigned")
    @Operation(description = "Get active trainers not assigned to trainee")
    public List<Trainer> getUnassigned(@PathVariable String username) {
        return traineeService.getNotAssignedActiveTrainers(username);
    }

    @PutMapping("/{username}/trainers")
    @Operation(description = "Update trainer list for trainee")
    public List<Trainer> updateTrainers(@PathVariable String username, @RequestBody List<String> trainerUsernames) {
        return traineeService.updateTrainerList(username, trainerUsernames);
    }

    @GetMapping("/{username}/trainings")
    @Operation(description = "Get trainee's trainings with filters")
    public List<Training> getTrainings(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType
    ) {
        return trainingService.getTraineeTrainings(username, from, to, trainerName, trainingType);
    }
}
