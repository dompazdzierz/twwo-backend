package pl.pwr.dissertation.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.dissertation.persistance.entity.LecturerEntity;

@Repository
public interface LecturerRepository extends CrudRepository<LecturerEntity, Integer> {
}
