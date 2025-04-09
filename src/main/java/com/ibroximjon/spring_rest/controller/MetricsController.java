package com.ibroximjon.spring_rest.controller;

import com.ibroximjon.spring_rest.metrics.CustomMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    @Autowired
    private  CustomMetrics customMetrics;

    // Example endpoint that triggers custom metrics recording
    @GetMapping("/trigger-metrics")
    public String triggerMetrics() {
        customMetrics.recordMetric();  // Recording custom metrics

        return "Metrics triggered!";
    }
}
