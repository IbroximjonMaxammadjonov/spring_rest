package com.ibroximjon.spring_rest.indicator;

import com.ibroximjon.spring_rest.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Autowired
    private HealthCheckRepository healthCheckRepository;  // JPA Repository to interact with the DB

    @Override
    @Transactional
    public Health health() {
        boolean isHealthy = checkDatabaseConnection();  // Perform health check logic

        if (isHealthy) {
            return Health.up().withDetail("custom", "Service is healthy").build();
        } else {
            return Health.down().withDetail("custom", "Service is not healthy").build();
        }
    }

    // JPA-based health check to ensure the database is reachable
    private boolean checkDatabaseConnection() {
        try {
            // Check if we can fetch a simple record, for example
            long count = healthCheckRepository.count();
            return count >= 0;  // If we successfully query, the database is up
        } catch (Exception e) {
            // If an exception occurs, the database is not reachable
            return false;
        }
    }
}
