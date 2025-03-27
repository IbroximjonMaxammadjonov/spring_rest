package com.ibroximjon.spring_rest.repository;

import com.ibroximjon.spring_rest.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

    List<Training> findByTrainees_Username(String username);

    List<Training> findByTrainers_Username(String username);

    List<Training> findByTrainingDateBetweenAndTrainees_Username(Date from, Date to, String traineeUsername);

    List<Training> findByTrainingDateBetweenAndTrainers_Username(Date from, Date to, String trainerUsername);
}
