package pl.pwr.dissertation.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private int topicId;
    private int authorId;
    private long timeUTC;
    private String text;
    private Integer fileId;
    private String filename;
}
