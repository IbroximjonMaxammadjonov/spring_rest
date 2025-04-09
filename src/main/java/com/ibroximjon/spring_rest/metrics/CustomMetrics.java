package com.ibroximjon.spring_rest.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    private final MeterRegistry meterRegistry;

    public CustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // Increment a custom counter
    public void incrementCustomCounter() {
        meterRegistry.counter("custom_counter").increment();
    }

    // Set a custom gauge value (e.g., number of active users)
    public void setCustomGauge(double value) {
        meterRegistry.gauge("custom_gauge", value);
    }

    // This is an example method that registers a custom metric when called
    public void recordMetric() {
        // Example of incrementing the counter
        incrementCustomCounter();

        // Example of setting a custom gauge
        setCustomGauge(Math.random() * 100);  // Example: Random value for demonstration
    }
}
