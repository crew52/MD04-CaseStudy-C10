package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.entity.Subject;
import codegym.c10.webservice.model.repository.ISubjectRepository;
import codegym.c10.webservice.model.service.iface.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    private ISubjectRepository iSubjectRepository;

    @Override
    public Iterable<Subject> findAll() {
        return iSubjectRepository.findAll();
    }

    @Override
    public Subject save(Subject T) {
        return null;
    }

    @Override
    public Optional<Subject> findById(Integer id) {
        return iSubjectRepository.findById(id);
    }

    @Override
    public void remove(Integer id) {

    }
}
