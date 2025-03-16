package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService{
    @Autowired
    private IStudentRepository iStudentRepository;


    @Override
    public List<Student> getAll() {
        return iStudentRepository.findAll();
    }

    @Override
    public List<Student> searchByName(String name) {
        return iStudentRepository.findByNameContaining(name);
    }

    @Override
    public List<Student> getByClassId(Integer classId) {
        return iStudentRepository.findByClassEntityId(classId);
    }

    @Override
    public Student add(Student entity) {
        return iStudentRepository.save(entity);
    }

    @Override
    public Student update(Integer id, Student entity) {
        Optional<Student> existingStudent = iStudentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(entity.getName());
            student.setDob(entity.getDob());
            student.setGender(entity.getGender());
            student.setAddress(entity.getAddress());
            student.setClassEntity(entity.getClassEntity());
            student.setParentContact(entity.getParentContact());
            return iStudentRepository.save(student);


        }else {
            throw new RuntimeException("Student not found" + id);
        }
    }

    @Override
    public void delete(Integer id) {
        if (iStudentRepository.existsById(id)){
            iStudentRepository.deleteById(id);
        }else {
            throw new RuntimeException("Student not found" + id);
        }
    }

    @Override
    public Student getById(Integer id) {
        return iStudentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found" + id));

    }
}

