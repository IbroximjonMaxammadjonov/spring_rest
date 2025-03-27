package com.ibroximjon.spring_rest.repository;

import com.ibroximjon.spring_rest.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Optional<Trainer> findByUsername(String username);
    List<Trainer> findByIsActiveTrue();
}
