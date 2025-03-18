package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.TeacherProfileDTO;
import codegym.c10.webservice.model.service.iface.ITeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachersProfile")
@CrossOrigin("*")
public class TeacherProfileController {
    @Autowired
    private ITeacherProfileService teacherProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<TeacherProfileDTO> getTeacherProfileByUserId(@PathVariable("userId") Integer userId) {
        try {
            TeacherProfileDTO teacherDTO = teacherProfileService.findTeacherByUserId(userId);
            return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<TeacherProfileDTO> updateTeacherProfileByUserId(
            @PathVariable("userId") Integer userId,
            @RequestBody TeacherProfileDTO teacherDTO) {
        try {
            TeacherProfileDTO updatedTeacher = teacherProfileService.updateTeacherByUserId(userId, teacherDTO);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
