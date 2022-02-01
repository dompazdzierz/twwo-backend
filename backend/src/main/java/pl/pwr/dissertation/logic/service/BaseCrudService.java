package pl.pwr.dissertation.logic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pwr.dissertation.logic.domain.User;

import java.util.Optional;

public interface BaseCrudService<T, TIndex, TSearchQuery> {

    T create(T obj);

    T update(T obj);

    Optional<T> findById(int id);

    Page<TIndex> findAll(TSearchQuery query, User user, Pageable pageable);

    void deleteById(int id);

}
