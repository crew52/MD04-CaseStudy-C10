const BASE_URL = 'http://localhost:8080';

// Giả sử userId được lưu trong localStorage sau khi đăng nhập
const userId = localStorage.getItem('userId') || 1; // Thay bằng logic thực tế để lấy userId

// Dữ liệu mặc định cho giáo viên (dùng để test)
const defaultTeacher = {
    name: "Nguyễn Văn A",
    dob: "1980-01-01",
    gender: "MALE",
    phone: "0123456789",
    email: "nguyenvana@example.com",
    subjectName: "MATHEMATICS",
    username: "teacher1"
};

// Tải thông tin giáo viên và hiển thị trên thẻ
function loadTeacherProfile() {
    $.ajax({
        url: `${BASE_URL}/api/teachersProfile/${userId}`,
        method: 'GET',
        success: function (teacher) {
            displayTeacherData(teacher);
        },
        error: function (xhr) {
            console.error('Error loading teacher profile:', xhr);
            alert('Không thể tải thông tin giáo viên từ server! Hiển thị dữ liệu mặc định để test.');
            displayTeacherData(defaultTeacher);
        }
    });
}

// Hiển thị dữ liệu lên giao diện
function displayTeacherData(teacher) {
    // Điền thông tin vào thẻ
    $('#cardName').text(teacher.name || 'Chưa có dữ liệu');
    $('#cardDob').text(teacher.dob || 'Chưa có dữ liệu');
    $('#cardGender').text(teacher.gender === 'MALE' ? 'Nam' : teacher.gender === 'FEMALE' ? 'Nữ' : 'Chưa có dữ liệu');
    $('#cardPhone').text(teacher.phone || 'Chưa có dữ liệu');
    $('#cardEmail').text(teacher.email || 'Chưa có dữ liệu');
    $('#cardSubject').text(teacher.subjectName || 'Chưa có bộ môn');
    $('#cardUsername').text(teacher.username || 'Chưa có tên đăng nhập');

    // Điền thông tin vào form chỉnh sửa (chỉ các trường được phép chỉnh sửa)
    $('#teacherName').val(teacher.name);
    $('#teacherDob').val(teacher.dob);
    $('#teacherGender').val(teacher.gender);
    $('#teacherPhone').val(teacher.phone);
    $('#teacherEmail').val(teacher.email);
}

// Hiển thị/Ẩn form chỉnh sửa
function toggleEditForm() {
    const editForm = $('#editForm');
    if (editForm.is(':visible')) {
        editForm.hide();
        loadTeacherProfile(); // Tải lại dữ liệu để reset form về giá trị ban đầu
    } else {
        editForm.show();
    }
}

// Cập nhật thông tin giáo viên
function updateTeacherProfile() {
    // Chỉ lấy các trường được phép chỉnh sửa
    const teacherData = {
        name: $('#teacherName').val(),
        dob: $('#teacherDob').val(),
        gender: $('#teacherGender').val(),
        phone: $('#teacherPhone').val(),
        email: $('#teacherEmail').val()
    };

    // Kiểm tra dữ liệu đầu vào
    if (!teacherData.name || !teacherData.dob || !teacherData.gender || !teacherData.phone || !teacherData.email) {
        alert('Vui lòng điền đầy đủ thông tin!');
        return;
    }

    $.ajax({
        url: `${BASE_URL}/api/teachersProfile/${userId}`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(teacherData),
        success: function (response) {
            alert('Cập nhật thông tin giáo viên thành công!');
            toggleEditForm(); // Ẩn form sau khi lưu
            loadTeacherProfile(); // Tải lại thông tin để cập nhật thẻ
        },
        error: function (xhr) {
            console.error('Error updating teacher profile:', xhr);
            alert('Không thể cập nhật thông tin giáo viên: ' + (xhr.responseText || 'Lỗi không xác định'));
        }
    });
}

// Tải thông tin khi trang được tải
$(document).ready(function () {
    loadTeacherProfile();
});