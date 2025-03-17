package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.entity.Subject;
import codegym.c10.webservice.model.service.iface.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin("*")
public class SubjectController {
    @Autowired
    private ISubjectService iSubjectService;

    @GetMapping
    public ResponseEntity<Iterable<Subject>> findAllType() {
        List<Subject> subjects = (List<Subject>) iSubjectService.findAll();
        if (subjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> findTypeById(@PathVariable Integer id) {
        Optional<Subject> subjectOptional = iSubjectService.findById(id);
        return subjectOptional.map(subject -> new ResponseEntity<>(subject, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
