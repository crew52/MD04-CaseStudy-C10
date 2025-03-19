package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.dto.TeacherProfileDTO;
import codegym.c10.webservice.model.service.IGenerateService;

public interface ITeacherProfileService extends IGenerateService<TeacherProfileDTO> {
    TeacherProfileDTO findTeacherByUserId(Integer userId);
    TeacherProfileDTO updateTeacherByUserId(Integer userId, TeacherProfileDTO dto);
    TeacherProfileDTO findTeacherDetailsByUserId(Integer userId);
}
