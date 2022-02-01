package pl.pwr.dissertation.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@DiscriminatorValue("student")
@AllArgsConstructor
@Audited
public class StudentEntity extends UserEntity {

    @Builder(builderMethodName = "userBuilder")
    public StudentEntity(String type, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean enabled, String username, String password, Set<String> authorities, String firstName, String lastName, String emailAddress) {
        super(type, createdAt, modifiedAt, enabled, username, password, authorities, firstName, lastName, emailAddress);
    }
}
