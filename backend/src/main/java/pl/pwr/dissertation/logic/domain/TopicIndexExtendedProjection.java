package pl.pwr.dissertation.logic.domain;

import lombok.Data;
import pl.pwr.dissertation.logic.domain.enums.Faculty;
import pl.pwr.dissertation.logic.domain.enums.Field;
import pl.pwr.dissertation.logic.domain.enums.Level;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;
import pl.pwr.dissertation.persistance.entity.LecturerEntity;


@Data
public class TopicIndexExtendedProjection extends TopicIndexProjection {

    private boolean userApplied;
    private Integer studentId;

    public TopicIndexExtendedProjection(Integer id, String name, String description, String descriptionEng, Level level, Field field, Faculty faculty, TopicStatus topicStatus, long applicationNumber, boolean free, LecturerEntity promoter, Boolean userApplied, Integer studentId) {
        super(id, name, description, descriptionEng, level, field, faculty, topicStatus, applicationNumber, free, promoter);
        this.userApplied = userApplied != null && userApplied;
        this.studentId = studentId;
    }
}
