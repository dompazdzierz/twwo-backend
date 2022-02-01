package pl.pwr.dissertation.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class TopicApplication extends BaseEntity {

    @ManyToOne
    private StudentEntity student;

    @ManyToOne
    private TopicEntity topicEntity;
}
