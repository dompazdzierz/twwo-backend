package pl.pwr.dissertation.persistance.entity;

import lombok.*;
import pl.pwr.dissertation.logic.domain.enums.Faculty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("lecturer")
public class LecturerEntity extends UserEntity {

    Faculty faculty;

    @Builder(builderMethodName = "lecturerBuilder")
    public LecturerEntity(LocalDateTime createdAt, LocalDateTime modifiedAt, boolean enabled, String username, String password, Set<String> authorities, String firstName, String lastName, String emailAddress) {
        super(createdAt, modifiedAt, enabled, username, password, authorities, firstName, lastName, emailAddress);
    }
}
