const BASE_URL = 'http://localhost:8080';

// Giả sử userId được lưu trong localStorage sau khi đăng nhập
const userId = localStorage.getItem('userId') || 1; // Thay bằng logic thực tế để lấy userId

// Hàm lấy token từ localStorage
function getToken() {
    return localStorage.getItem('token');
}

// Tải thông tin giáo viên và lớp chủ nhiệm
function loadTeacherAndClassInfo() {
    let token = getToken();
    if (token == null) {
        window.location.href = "/html/login/login.html"; // Chuyển hướng nếu không có token
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: `${BASE_URL}/api/teachersProfile/details/${userId}`,
            method: 'GET',
            success: function (teacher) {
                displayTeacherAndClassInfo(teacher);
            },
            error: function (xhr) {
                console.error('Error loading teacher profile:', xhr);
                alert('Không thể tải thông tin giáo viên và lớp!');
                $('#teacherName').text('Chưa có dữ liệu');
                $('#className').text('Chưa có lớp');
            }
        });
    }
}

// Hiển thị thông tin giáo viên và lớp
function displayTeacherAndClassInfo(teacher) {
    $('#teacherName').text(teacher.name || 'Chưa có dữ liệu');
    if (teacher.classInfo) {
        $('#className').text(teacher.classInfo.className);
        // Gán sự kiện nhấp vào tên lớp để hiển thị danh sách học sinh
        $('#className').off('click').on('click', function () {
            loadStudents(teacher.classInfo.id);
        });
    } else {
        $('#className').text('Chưa có lớp');
        $('#className').removeClass('class-link'); // Xóa style liên kết nếu không có lớp
    }
}

// Tải danh sách học sinh trong lớp
function loadStudents(classId) {
    // Nếu bảng đang hiển thị, ẩn nó đi và thoát hàm (toggle)
    if ($('#studentTable').is(':visible')) {
        $('#studentTable').hide();
        return;
    }

    let token = getToken();
    if (token == null) {
        window.location.href = "/html/login/login.html"; // Chuyển hướng nếu không có token
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: `${BASE_URL}/api/teachersProfile/details/${userId}`,
            method: 'GET',
            success: function (teacher) {
                if (teacher.classInfo && teacher.classInfo.id === classId && teacher.students) {
                    displayStudents(teacher.students);
                } else {
                    $('#studentTableBody').html('<tr><td colspan="6" class="text-center">Không có học sinh nào trong lớp này</td></tr>');
                    $('#studentTable').show();
                }
            },
            error: function (xhr) {
                console.error('Error loading students:', xhr);
                $('#studentTableBody').html('<tr><td colspan="6" class="text-center">Lỗi khi tải danh sách học sinh</td></tr>');
                $('#studentTable').show();
            }
        });
    }
}

// Hiển thị danh sách học sinh
function displayStudents(students) {
    const tbody = $('#studentTableBody');
    tbody.empty(); // Xóa dữ liệu cũ

    if (students && students.length > 0) {
        students.forEach(student => {
            const row = `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.dob}</td>
                    <td>${student.gender === 'MALE' ? 'Nam' : 'Nữ'}</td>
                    <td>${student.parentContact || 'Chưa có dữ liệu'}</td>
                </tr>
            `;
            tbody.append(row);
        });
    } else {
        tbody.html('<tr><td colspan="6" class="text-center">Không có học sinh nào trong lớp này</td></tr>');
    }
    $('#studentTable').show(); // Hiển thị bảng
}

// Tải thông tin khi trang được tải
$(document).ready(function () {
    loadTeacherAndClassInfo();
});