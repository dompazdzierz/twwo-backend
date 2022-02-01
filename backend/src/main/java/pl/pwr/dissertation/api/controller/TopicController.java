package pl.pwr.dissertation.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.pwr.dissertation.logic.domain.ChangeVersion;
import pl.pwr.dissertation.logic.domain.Topic;
import pl.pwr.dissertation.logic.domain.TopicIndexProjection;
import pl.pwr.dissertation.logic.domain.search.TopicSearchQuery;

import java.util.List;


public interface TopicController extends BaseCrudController<Topic, TopicIndexProjection, TopicSearchQuery> {

    @Operation(description = "Confirm topic and proceed to the Programme Board")
    @PostMapping("/{topicId}/confirm")
    void confirm(@PathVariable int topicId);

    @PostMapping("/{topicId}/apply")
    void applyToTopic(@PathVariable int topicId);

    @PostMapping("/{topicId}/accept")
    void accept(@PathVariable int topicId);

    @PostMapping("/accept")
    void acceptAll(@RequestBody List<Integer> topicIds);

    @PostMapping("/{topicId}/accept/student/{studentId}")
    void acceptStudent(@PathVariable int studentId, @PathVariable int topicId);

    @GetMapping("/{topicId}/history")
    List<ChangeVersion<Object>> getHistoryOfChanges(@PathVariable int topicId);

    @GetMapping("/my")
    Page<TopicIndexProjection> getMyTopics(Pageable pageable, TopicSearchQuery query);
}
