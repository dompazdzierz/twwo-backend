package pl.pwr.dissertation.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.pwr.dissertation.persistance.entity.UserEntity;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<UserEntity> {

    /**
     * This method is used by @CreatedBy and @ModifiedBy on Entity field
     *
     * @return Optional of current logged user. If user isn't logged return Optional.empty()
     */
    @Override
    public Optional<UserEntity> getCurrentAuditor() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            return Optional.of((UserEntity) principal);
        }
        return Optional.empty();

    }

}
