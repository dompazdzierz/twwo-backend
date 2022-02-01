package pl.pwr.dissertation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class DissertationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DissertationApplication.class, args);
    }

}
