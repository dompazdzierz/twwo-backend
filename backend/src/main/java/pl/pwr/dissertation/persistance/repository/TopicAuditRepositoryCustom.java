package pl.pwr.dissertation.persistance.repository;

import pl.pwr.dissertation.logic.domain.ChangeVersion;

import java.util.List;

public interface TopicAuditRepositoryCustom {

    List<ChangeVersion<Object>> getTopicHistory(int id);
}
