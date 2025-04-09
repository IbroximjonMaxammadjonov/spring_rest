package com.ibroximjon.spring_rest.service;

import com.ibroximjon.spring_rest.model.Trainee;
import com.ibroximjon.spring_rest.repository.TraineeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    public void testRegisterTrainee_success() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setUsername("john_doe");
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        when(traineeRepository.save(trainee)).thenReturn(trainee);

        // Act
        Trainee result = traineeService.registerTrainee(trainee);

        // Assert
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(traineeRepository, times(1)).save(trainee);  // Verify the save method is called once
    }

    @Test
    public void testRegisterTrainee_failure() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setUsername("jane_doe");

        when(traineeRepository.save(trainee)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            traineeService.registerTrainee(trainee);
        });

        assertEquals("Failed to register trainee", exception.getMessage());
    }
}
