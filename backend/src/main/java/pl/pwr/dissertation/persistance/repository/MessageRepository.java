package pl.pwr.dissertation.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.dissertation.persistance.entity.MessageEntity;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

    List<MessageEntity> findAllByTopicId(int dissertationId);
}
