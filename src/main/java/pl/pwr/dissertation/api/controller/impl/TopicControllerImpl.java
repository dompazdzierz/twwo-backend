package pl.pwr.dissertation.api.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwr.dissertation.api.controller.TopicController;
import pl.pwr.dissertation.api.exception.BadRequestException;
import pl.pwr.dissertation.api.exception.NotFoundException;
import pl.pwr.dissertation.logic.domain.ChangeVersion;
import pl.pwr.dissertation.logic.domain.Topic;
import pl.pwr.dissertation.logic.domain.TopicIndexProjection;
import pl.pwr.dissertation.logic.domain.User;
import pl.pwr.dissertation.logic.domain.enums.TopicStatus;
import pl.pwr.dissertation.logic.domain.search.TopicSearchQuery;
import pl.pwr.dissertation.logic.service.TopicService;
import pl.pwr.dissertation.persistance.entity.Role;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicControllerImpl extends BaseController implements TopicController {

    private final TopicService topicService;

    @Override
    public Topic create(Topic obj) {
        return topicService.create(obj);
    }

    @Override
    public Topic update(Topic obj) {
        return topicService.update(obj);
    }

    @Override
    public Topic findById(int id) {
        return topicService.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Page<TopicIndexProjection> findAll(TopicSearchQuery query, Pageable pageable) {
        User user = getCurrentUser();
        return topicService.findAll(query, user, pageable);
    }

    @Override
    public void deleteById(int id) {
        topicService.deleteById(id);
    }

    @Override
    @RolesAllowed(Role.LECTURER)
    public void confirm(int topicId) {
        topicService.changeStatus(topicId, TopicStatus.CONFIRMED);
    }

    @Override
    public void applyToTopic(int topicId) {
        User currentUser = getCurrentUser();
        topicService.applyToTopic(topicId, currentUser);
    }

    @Override
    @RolesAllowed(Role.PROGRAMME_BOARD)
    public void accept(int topicId) {
        topicService.changeStatus(topicId, TopicStatus.ACCEPTED_PB);
    }

    @Override
    @RolesAllowed(Role.PROGRAMME_BOARD)
    public void acceptAll(List<Integer> topicIds) {
        topicService.changeStatus(topicIds, TopicStatus.ACCEPTED_PB);
    }

    @Override
    public void acceptStudent(int studentId, int topicId) {
        User currentUser = getCurrentUser();
        this.topicService.acceptStudent(currentUser, topicId, studentId);
    }

    @Override
    public List<ChangeVersion<Object>> getHistoryOfChanges(int topicId) {
        return this.topicService.getHistory(topicId);
    }

    @Override
    public Page<TopicIndexProjection> getMyTopics(Pageable pageable, TopicSearchQuery query) {
        User user = getCurrentUser();
        return this.topicService.getStudentTopics(user, query, pageable);
    }

}
