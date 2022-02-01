package pl.pwr.dissertation.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.dissertation.persistance.entity.TopicApplication;

@Repository
public interface TopicApplicationRepository extends CrudRepository<TopicApplication, Integer> {
}
