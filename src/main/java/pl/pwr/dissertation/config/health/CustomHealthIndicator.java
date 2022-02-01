package pl.pwr.dissertation.config.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomHealthIndicator implements HealthIndicator {

    @Override

    public Health health() {

        try {
            Health.Builder healthBuilder;
            healthBuilder = Health.up();// blogService.isBlogEndpointUp() ? Health.up() : Health.down();

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
//            LocalDateTime availableSince = blogService.getAvailableSince();
            LocalDateTime availableSince = LocalDateTime.now();
            return healthBuilder.withDetail("up since", availableSince.format(dateTimeFormatter))
                    .build();

        } catch (Exception e) {
            return Health.down(e).build();
        }

    }


}
