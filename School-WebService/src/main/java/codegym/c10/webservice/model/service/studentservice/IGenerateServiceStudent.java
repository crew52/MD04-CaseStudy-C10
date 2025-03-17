package codegym.c10.webservice.model.service.studentservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGenerateServiceStudent<T> {
    Page<T> findAll(Pageable pageable);
    T save(T t);
    void deleteById(Integer id);
    T findById(Integer id);
}