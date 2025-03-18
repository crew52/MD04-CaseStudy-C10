const BASE_URL = 'http://localhost:8080';
let currentPage = 0;
const pageSize = 10;

// Hàm mới: Tải danh sách giáo viên và điền vào dropdown
function loadTeachersDropdown(selectedId = '') {
    $.ajax({
        url: `${BASE_URL}/api/teachers`,
        method: 'GET',
        success: function (teachers) {
            const teacherSelect = $('#teacherId');
            teacherSelect.empty(); // Xóa các option cũ
            teacherSelect.append('<option value="">Chọn giáo viên</option>'); // Thêm option mặc định
            teachers.content.forEach(teacher => {
                const isSelected = teacher.id === selectedId ? 'selected' : '';
                teacherSelect.append(
                    `<option value="${teacher.id}" ${isSelected}>${teacher.name}</option>`
                );
            });
        },
        error: function (xhr) {
            console.error('Error loading teachers:', xhr);
            alert('Không thể tải danh sách giáo viên!');
        }
    });
}

function loadClasses(page = currentPage, searchTerm = '') {
    let url = `${BASE_URL}/api/classes/page?page=${page}&size=${pageSize}`;
    if (searchTerm) {
        url += `&className=${encodeURIComponent(searchTerm)}`;
    }

    $.ajax({
        url: url,
        method: 'GET',
        success: function (response) {
            const classes = response.content;
            const totalPages = response.totalPages;
            const tbody = $('#classTableBody');
            tbody.empty();

            classes.forEach(cls => {
                const row = `
                    <tr>
                        <td>${cls.id}</td>
                        <td>${cls.className}</td>
                        <td>${cls.gradeLevel}</td>
                        <td>${cls.teacherName || 'Chưa có giáo viên'}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="prepareEditClass(${cls.id})">
                                <i class="fas fa-edit"></i> Sửa
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="deleteClass(${cls.id})">
                                <i class="fas fa-trash"></i> Xóa
                            </button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });

            $('#pageInfo').text(`Trang ${page + 1} / ${totalPages}`);
            currentPage = page;
            $('button:contains("Trang Trước")').prop('disabled', page === 0);
            $('button:contains("Trang Sau")').prop('disabled', page === totalPages - 1);
        },
        error: function (xhr) {
            console.error('Error loading classes:', xhr);
            alert('Không thể tải danh sách lớp học!');
        }
    });
}

function searchClasses() {
    const searchTerm = $('#searchClassName').val().trim();
    loadClasses(0, searchTerm);
}

function changePage(delta) {
    const newPage = currentPage + delta;
    if (newPage >= 0) {
        loadClasses(newPage, $('#searchClassName').val().trim());
    }
}

function prepareAddClass() {
    $('#classModalLabel').text('Thêm Lớp học');
    $('#classForm')[0].reset();
    $('#classId').val('');
    loadTeachersDropdown(); // Tải danh sách giáo viên khi mở modal thêm
    $('#classModal').modal('show');
}

function prepareEditClass(id) {
    $.ajax({
        url: `${BASE_URL}/api/classes/${id}`,
        method: 'GET',
        success: function (cls) {
            $('#classModalLabel').text('Cập Nhật Lớp học');
            $('#classId').val(cls.id);
            $('#className').val(cls.className);
            $('#gradeLevel').val(cls.gradeLevel);
            loadTeachersDropdown(cls.teacherId); // Tải danh sách giáo viên và chọn sẵn teacherId
            $('#classModal').modal('show');
        },
        error: function (xhr) {
            console.error('Error fetching class:', xhr);
            alert('Không thể tải thông tin lớp học!');
        }
    });
}

function saveClass() {
    const id = $('#classId').val();
    const classData = {
        className: $('#className').val().trim(),
        gradeLevel: $('#gradeLevel').val(),
        teacherId: $('#teacherId').val() ? parseInt($('#teacherId').val()) : null // Lấy teacherId từ dropdown
    };

    if (!classData.className || !classData.gradeLevel) {
        alert('Tên lớp và cấp độ là bắt buộc!');
        return;
    }

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${BASE_URL}/api/classes/${id}` : `${BASE_URL}/api/classes`;

    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(classData),
        success: function (response) {
            $('#classModal').modal('hide');
            loadClasses();
            alert(id ? 'Cập nhật lớp học thành công!' : 'Thêm lớp học thành công!');
        },
        error: function (xhr) {
            console.error('Error saving class:', xhr);
            let errorMessage = 'Có lỗi xảy ra khi lưu lớp học!';
            if (xhr.responseJSON && xhr.responseJSON.className) {
                errorMessage = xhr.responseJSON.className;
            } else if (xhr.responseText) {
                errorMessage = xhr.responseText;
            }
            alert(errorMessage);
        }
    });
}

function deleteClass(id) {
    if (confirm('Bạn có chắc muốn xóa lớp học này?')) {
        $.ajax({
            url: `${BASE_URL}/api/classes/${id}`,
            method: 'DELETE',
            success: function () {
                loadClasses();
                alert('Xóa lớp học thành công!');
            },
            error: function (xhr) {
                console.error('Error deleting class:', xhr);
                alert('Không thể xóa lớp học: ' + (xhr.responseText || 'Unknown error'));
            }
        });
    }
}

// Reset dữ liệu về trạng thái ban đầu
function resetData() {
    currentPage = 0; // Đặt lại về trang đầu
    $('#searchClassName').val(''); // Xóa ô tìm kiếm
    loadClasses(currentPage, ''); // Tải lại danh sách mặc định
}

$(document).ready(function () {
    loadClasses();
});