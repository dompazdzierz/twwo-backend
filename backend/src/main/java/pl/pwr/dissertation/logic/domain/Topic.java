package pl.pwr.dissertation.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.dissertation.logic.domain.enums.Faculty;
import pl.pwr.dissertation.logic.domain.enums.Field;
import pl.pwr.dissertation.logic.domain.enums.Level;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {

    private Integer id;
    private String name;
    private String description;
    private String descriptionEng;
    private UserDto promoter;
    private Level level;
    private Field field;
    private Faculty faculty;
    private TopicStatus status;

}
