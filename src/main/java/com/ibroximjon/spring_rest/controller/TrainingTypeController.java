package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.model.TrainingType;
import com.ibroximjon.spring_rest.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-types")
@Tag(name = "Training Type Management")
public class TrainingTypeController {

    @Autowired
    private TrainingTypeService trainingTypeService;

    @GetMapping
    @Operation(description = "Get list of all training types")
    public List<TrainingType> getAllTrainingTypes() {
        return trainingTypeService.getAllTypes();
    }
}
