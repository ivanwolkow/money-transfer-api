package org.example.healthcheck;

import com.codahale.metrics.health.HealthCheck;

/**
 * Dummy health-check
 */
public class DefaultHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
