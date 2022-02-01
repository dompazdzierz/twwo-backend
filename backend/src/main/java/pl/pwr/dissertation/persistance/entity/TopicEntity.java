package pl.pwr.dissertation.persistance.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import pl.pwr.dissertation.logic.domain.enums.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Builder
public class TopicEntity extends BaseEntity {

    private String name;
    private String description;
    private String descriptionEng;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    private Field field;

    @Enumerated(EnumType.STRING)
    private Term term;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;

    @ElementCollection
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<String> tags;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private LecturerEntity promoter;

    @OneToMany(mappedBy = "topicEntity")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<TopicApplication> applications;

    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private StudentEntity student;
//
//    @OneToOne
//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
//    private DissertationEntity dissertation;

    @Override
    public String toString() {
        return "TopicEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", descriptionEng='" + descriptionEng + '\'' +
                ", level=" + level +
                ", faculty=" + faculty +
                ", field=" + field +
                ", term=" + term +
                '}';
    }
}
