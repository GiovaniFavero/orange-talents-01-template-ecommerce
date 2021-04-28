package br.com.zup.mercadolivre.shared.config.security.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppHealthCheck implements HealthIndicator {
    @Override
    public Health getHealth(boolean includeDetails) {
        return this.health();
    }

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("version", "1.4.5");
        details.put("description", "Custom Health Check!");
        details.put("address", "192.168.2.1");
        return Health.status(Status.UP).withDetails(details).build();
    }
}
