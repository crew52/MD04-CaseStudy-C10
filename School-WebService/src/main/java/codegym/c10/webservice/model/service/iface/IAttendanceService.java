package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.service.IGenerateService;
import org.springframework.data.domain.Page;

public interface IAttendanceService extends IGenerateService<AttendanceDTO> {
    Page<AttendanceDTO> searchAttendances(String className, String studentName, int page, int size);
    Page<AttendanceDTO> getTeacherAttendances(Integer teacherId, String className, String studentName, int page, int size);
}