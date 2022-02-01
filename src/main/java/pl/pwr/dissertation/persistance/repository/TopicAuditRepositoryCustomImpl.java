package pl.pwr.dissertation.persistance.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.*;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.stereotype.Repository;
import pl.pwr.dissertation.logic.domain.Change;
import pl.pwr.dissertation.logic.domain.ChangeVersion;
import pl.pwr.dissertation.logic.service.impl.TopicServiceImpl;
import pl.pwr.dissertation.persistance.entity.TopicEntity;

import javax.persistence.EntityManager;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class TopicAuditRepositoryCustomImpl implements TopicAuditRepositoryCustom {

    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    @Override
    public List<ChangeVersion<Object>> getTopicHistory(int id) {

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Number> revisions = auditReader.getRevisions(TopicEntity.class, id);

        Map<Number, DefaultRevisionEntity> revisions1 = auditReader.findRevisions(DefaultRevisionEntity.class, new HashSet<>(revisions));

        List<TopicWithRevision> topicEntities = revisions.stream().map(rev ->
                TopicWithRevision.builder()
                        .topic(auditReader.find(TopicEntity.class, id, rev))
                        .revision(revisions1.get(rev)).build()
        ).toList();

        Map<String, Object> prev = new HashMap<>();
        Map<String, Object> current;

        List<ChangeVersion<Object>> changeVersions = new ArrayList<>();

        for (TopicWithRevision topicWithRevision : topicEntities) {
            current = objectMapper.convertValue(TopicServiceImpl.mapToDomain(topicWithRevision.getTopic()), HashMap.class);

            List<Change<Object>> changes = new ArrayList<>();

            MapDifference<String, Object> difference = Maps.difference(prev, current);
            for (Map.Entry<String, Object> stringObjectEntry : difference.entriesOnlyOnRight().entrySet()) {
                var value = stringObjectEntry.getValue();
                Change<Object> change = Change.builder().fieldName(stringObjectEntry.getKey()).oldValue(null).newValue(value).type(Change.Type.ADD).build();
                changes.add(change);
            }

            for (Map.Entry<String, Object> stringObjectEntry : difference.entriesOnlyOnLeft().entrySet()) {
                var value = stringObjectEntry.getValue();
                Change<Object> change = Change.builder().fieldName(stringObjectEntry.getKey()).oldValue(value).newValue(null).type(Change.Type.DELETE).build();
                changes.add(change);
            }

            for (Map.Entry<String, MapDifference.ValueDifference<Object>> stringValueDifferenceEntry : difference.entriesDiffering().entrySet()) {
                var value = stringValueDifferenceEntry.getValue();
                Change<Object> change = Change.builder().fieldName(stringValueDifferenceEntry.getKey())
                        .oldValue(value.leftValue())
                        .newValue(value.rightValue())
                        .type(Change.Type.MODIFY).build();
                changes.add(change);
            }

            changeVersions.add(ChangeVersion.builder().revision(topicWithRevision.getRevision()).changeList(changes).build());

            prev = current;
        }

        return changeVersions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TopicWithRevision {
        private DefaultRevisionEntity revision;
        private TopicEntity topic;
    }
}
