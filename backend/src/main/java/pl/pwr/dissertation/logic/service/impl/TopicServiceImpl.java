package pl.pwr.dissertation.logic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.dissertation.api.exception.BadRequestException;
import pl.pwr.dissertation.api.exception.ForbiddenException;
import pl.pwr.dissertation.logic.domain.*;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;
import pl.pwr.dissertation.logic.domain.search.TopicSearchQuery;
import pl.pwr.dissertation.logic.service.TopicService;
import pl.pwr.dissertation.persistance.entity.TopicApplication;
import pl.pwr.dissertation.persistance.entity.TopicEntity;
import pl.pwr.dissertation.persistance.repository.StudentRepository;
import pl.pwr.dissertation.persistance.repository.TopicApplicationRepository;
import pl.pwr.dissertation.persistance.repository.TopicRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final StudentRepository studentRepository;
    private final TopicApplicationRepository topicApplicationRepository;

    @Override
    public Topic create(Topic obj) {
        var entity = mapToEntity(obj, false);
        entity.setStatus(TopicStatus.DRAFT);
        var saved = this.save(entity);
        return mapToDomain(saved);
    }

    @Override
    public Topic update(Topic obj) {
        TopicEntity topicEntity = this.topicRepository.findById(obj.getId()).orElseThrow(BadRequestException::new);
        BeanUtils.copyProperties(obj, topicEntity);
        var saved = this.save(topicEntity);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Topic> findById(int id) {
        return this.topicRepository.findById(id).map(TopicServiceImpl::mapToDomain);
    }

    @Override
    public Page<TopicIndexProjection> findAll(TopicSearchQuery query, User user, Pageable pageable) {

        boolean isStudent = Objects.equals(user.getType(), "student");
        if (query.getStudentId() != null
                && isStudent
                && !Objects.equals(user.getId(), query.getStudentId())
        ) {
            throw new BadRequestException("");
        }

        Page<TopicIndexProjection> allIndexWithQuery;

        if (isStudent && query.getStudentId() != null) {
            allIndexWithQuery = this.topicRepository.findAllIndexWithQueryPerStudent(query, user.getId(), pageable)
                    .map(extendedTopic -> mapStudentTopicIndex(extendedTopic, user.getId()));
        } else {
            allIndexWithQuery = this.topicRepository.findAllIndexWithQuery(query, pageable);
        }

        return allIndexWithQuery;
    }

    private TopicIndexProjection mapStudentTopicIndex(TopicIndexExtendedProjection extendedTopic, int studentId) {
        var topic = new TopicIndexProjection();
        BeanUtils.copyProperties(extendedTopic, topic, "status");

        var topicStudId = extendedTopic.getStudentId();

        if (topicStudId == null && extendedTopic.isUserApplied()) {
            topic.setStatus(TopicStatus.APPLICATION_IN_PROGRESS);
        } else if (topicStudId == null) {
            topic.setStatus(extendedTopic.getStatus());
        } else if (topicStudId == studentId) {
            topic.setStatus(TopicStatus.IN_PROGRESS);
        } else if (extendedTopic.isUserApplied()) {
            topic.setStatus(TopicStatus.REJECTED_PB);
        } else {
            topic.setStatus(extendedTopic.getStatus());
        }

        return topic;
    }

    private void updateStatus(TopicIndexProjection topic, User user) {
//        if(topic.getS)
    }

    @Override
    public void deleteById(int id) {
        this.topicRepository.deleteById(id);
    }

    public static Topic mapToDomain(TopicEntity entity) {
        var topic = new Topic();
        BeanUtils.copyProperties(entity, topic);
        if (entity.getPromoter() != null) {
            topic.setPromoter(LecturerServiceImpl.mapToDomain(entity.getPromoter()));
        }
        return topic;
    }

    private TopicEntity mapToEntity(Topic topic, boolean withId) {
        var entity = new TopicEntity();
        if (withId) {
            BeanUtils.copyProperties(topic, entity, "status");
        } else {
            BeanUtils.copyProperties(topic, entity, "id", "status");
        }
        return entity;
    }

    @Override
    public void applyToTopic(int topicId, User user) {

        TopicApplication topicApplication = TopicApplication
                .builder()
                .student(studentRepository.getById(user.getId()))
                .topicEntity(topicRepository.getById(topicId))
                .build();

        topicApplicationRepository.save(topicApplication);
    }

    @Override
    public List<ChangeVersion<Object>> getHistory(int topicId) {
        return topicRepository.getTopicHistory(topicId);
    }

    @Override
    public void changeStatus(int topicId, TopicStatus status) {
        TopicEntity topic = this.topicRepository.findById(topicId).orElseThrow(() -> new BadRequestException("Topic does not exist"));
        topic.setStatus(status);
        this.save(topic);
    }

    @Override
    public void changeStatus(List<Integer> topicId, TopicStatus status) {

        List<TopicEntity> allById = this.topicRepository.findAllById(topicId);
        for (TopicEntity topicEntity : allById) {
            topicEntity.setStatus(status);
        }
        this.topicRepository.saveAll(allById);
    }

    private TopicEntity save(TopicEntity toSave) {
        return this.topicRepository.save(toSave);
    }

    @Override
    public void acceptStudent(User currentUser, int topicId, int studentId) {
        TopicEntity topic = this.topicRepository.findById(topicId).orElseThrow(() -> new BadRequestException("Topic does not exist"));

        boolean isLecturerTopic = topic.getPromoter().getId() == currentUser.getId();

        if (!isLecturerTopic) {
            throw new ForbiddenException("");
        }

        var topicApplicationOptional = topic.getApplications().stream().filter(x -> x.getId() == studentId).findFirst();
        TopicApplication topicApplication = topicApplicationOptional.orElseThrow(BadRequestException::new);
        topic.setStudent(topicApplication.getStudent());

        this.save(topic);
    }

    @Override
    public Page<TopicIndexProjection> getStudentTopics(User user, TopicSearchQuery topicSearchQuery, Pageable pageable) {
        return this.topicRepository.findAllIndexWithQuery(topicSearchQuery, pageable);

    }
}
