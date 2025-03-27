package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.TrainingType;
import com.ibroximjon.spring_rest.repository.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Override
    public List<TrainingType> getAllTypes() {
        return trainingTypeRepository.findAll();
    }
}
