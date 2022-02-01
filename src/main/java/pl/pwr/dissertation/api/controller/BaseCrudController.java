package pl.pwr.dissertation.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


public interface BaseCrudController<TDto, TIndex, TSearchQuery> {

    @PostMapping
    TDto create(@RequestBody TDto obj);

    @PutMapping
    TDto update(@RequestBody TDto obj);

    @GetMapping("/{id}")
    TDto findById(@PathVariable int id);

    @GetMapping
    Page<TIndex> findAll(TSearchQuery query, Pageable pageable);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable int id);

}
