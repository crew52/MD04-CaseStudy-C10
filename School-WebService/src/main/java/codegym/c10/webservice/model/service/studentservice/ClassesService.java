package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.ClassesDTO;
import codegym.c10.webservice.model.entity.Classes;
import codegym.c10.webservice.model.repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesService implements IClassService{
    @Autowired
    private ClassesRepository classesRepository;

    // Chuyển từ Entity sang DTO
    private ClassesDTO convertToDTO(Classes cls) {
        ClassesDTO dto = new ClassesDTO();
        dto.setId(cls.getId());
        dto.setClassName(cls.getClassName());
        return dto;
    }

    // Chuyển từ DTO sang Entity
    private Classes convertToEntity(ClassesDTO dto) {
        Classes cls = new Classes();
        cls.setId(dto.getId());
        cls.setClassName(dto.getClassName());
        return cls;
    }

    @Override
    public Page<ClassesDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ClassesDTO findById(Integer id) {
        return classesRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public ClassesDTO save(ClassesDTO dto) {
        Classes cls = convertToEntity(dto);
        Classes savedClass = classesRepository.save(cls);
        return convertToDTO(savedClass);
    }

    @Override
    public ClassesDTO update(Integer id, ClassesDTO entity) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        classesRepository.deleteById(id);
    }

    @Override
    public List<ClassesDTO> findAll() {
        return classesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
