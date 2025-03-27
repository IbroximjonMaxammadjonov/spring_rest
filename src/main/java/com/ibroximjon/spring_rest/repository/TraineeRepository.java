package com.ibroximjon.spring_rest.repository;

import com.ibroximjon.spring_rest.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    Optional<Trainee> findByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);

}
