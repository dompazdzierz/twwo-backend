package pl.pwr.dissertation.logic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pwr.dissertation.logic.domain.ChangeVersion;
import pl.pwr.dissertation.logic.domain.Topic;
import pl.pwr.dissertation.logic.domain.TopicIndexProjection;
import pl.pwr.dissertation.logic.domain.User;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;
import pl.pwr.dissertation.logic.domain.search.TopicSearchQuery;

import java.util.List;

public interface TopicService extends BaseCrudService<Topic, TopicIndexProjection, TopicSearchQuery> {

    void applyToTopic(int topicId, User user);

    List<ChangeVersion<Object>> getHistory(int topicId);

    void changeStatus(int topicId, TopicStatus status);

    void changeStatus(List<Integer> topicId, TopicStatus status);

    void acceptStudent(User currentUser, int topicId, int studentId);

    Page<TopicIndexProjection> getStudentTopics(User user, TopicSearchQuery topicSearchQuery, Pageable pageable);
}
