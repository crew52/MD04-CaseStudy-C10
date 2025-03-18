package codegym.c10.webservice.model.service.studentservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenerateServiceStudent<T> {
    Page<T> findAll(Pageable pageable);
    T findById(Integer id);
    T save(T entity);
    T update(Integer id, T entity);
    void delete(Integer id);
    List<T> findAll();

}