package pl.pwr.dissertation.api.dto;

import lombok.Data;
import pl.pwr.dissertation.logic.domain.Topic;

@Data
public class TopicIndexDto extends Topic {

    public int applicationNumber;
    public boolean free;
}
