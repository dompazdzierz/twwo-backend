package pl.pwr.dissertation.logic.domain.search;

import lombok.Data;
import pl.pwr.dissertation.logic.domain.enums.*;

import java.util.Set;

@Data
public class TopicSearchQuery {

    public Term term;
    public Faculty faculty;
    public Field field;
    public Level level;
    public String promoter;
    public Integer promoterId;
    public TopicStatus topicStatus;
    public String tag;
    public Boolean free;
    public Integer studentId;
}
