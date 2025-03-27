package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.dto.TrainingRequestDTO;
import com.ibroximjon.spring_rest.model.Training;
import com.ibroximjon.spring_rest.model.TrainingType;
import com.ibroximjon.spring_rest.repository.TrainingTypeRepository;
import com.ibroximjon.spring_rest.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainings")
@Tag(name = "Training Management")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @PostMapping("/add")
    @Operation(description = "Add new training")
    public void addTraining(@RequestBody TrainingRequestDTO request) {
        Training training = new Training();
        training.setTrainingName(request.getTrainingName());
        training.setTrainingDate(request.getTrainingDate());
        training.setDuration(request.getDuration());

        TrainingType type = trainingTypeRepository.findById(request.getTrainingTypeId())
                .orElseThrow(() -> new RuntimeException("TrainingType not found"));

        training.setTrainingType(type);

        trainingService.addTraining(
                request.getTraineeUsername(),
                request.getTrainerUsername(),
                training
        );
    }
}
