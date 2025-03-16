package codegym.c10.webservice.model.service;

import java.util.Optional;

public interface IGenerateService<T> {
    Iterable<T> findAll();

    T save(T T);

    Optional<T> findById(Integer id);

    void remove(Integer id);
}
