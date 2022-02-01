package pl.pwr.dissertation.logic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.dissertation.logic.domain.enums.Faculty;
import pl.pwr.dissertation.logic.domain.enums.Field;
import pl.pwr.dissertation.logic.domain.enums.Level;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;
import pl.pwr.dissertation.logic.service.impl.LecturerServiceImpl;
import pl.pwr.dissertation.persistance.entity.LecturerEntity;

@Data
@NoArgsConstructor
public class TopicIndexProjection extends Topic {

    public int applicationNumber;
    public boolean free;


    public TopicIndexProjection(Integer id, String name, String description,
                                String descriptionEng, Level level, Field field,
                                Faculty faculty, TopicStatus topicStatus,
                                long applicationNumber, boolean free,
                                LecturerEntity promoter) {
        super(id, name, description, descriptionEng, LecturerServiceImpl.mapToDomain(promoter), level, field, faculty, topicStatus);
        this.applicationNumber = (int) applicationNumber;
        this.free = free;
    }

}
