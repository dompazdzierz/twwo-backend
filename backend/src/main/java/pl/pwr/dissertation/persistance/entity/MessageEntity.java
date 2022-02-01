package pl.pwr.dissertation.persistance.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends BaseEntity {

    private String text;

    @OneToOne
    private FileEntity file;

    @ManyToOne
    private TopicEntity topic;

}
