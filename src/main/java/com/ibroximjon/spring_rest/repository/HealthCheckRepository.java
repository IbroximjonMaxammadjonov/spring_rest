package com.ibroximjon.spring_rest.repository;

import com.ibroximjon.spring_rest.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckRepository extends JpaRepository<Trainee, Long> {
}
