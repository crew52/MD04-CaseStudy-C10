package codegym.c10.webservice.model.service.studentservice;

import java.util.List;

public interface IGenerateServiceStudent<T> {
    List<T> getAll();    // Hiển thị danh sách tất cả
    List<T> searchByName(String name);     // Tìm kiếm theo tên
    List<T> getByClassId(Integer classId);     // Tìm kiếm theo lớp
    T add(T entity); // Thêm mới
    T update(Integer id, T entity);    // Cập nhật
    void delete(Integer id);    // Xóa
    T getById(Integer id);    // Lấy thông tin theo ID
}
