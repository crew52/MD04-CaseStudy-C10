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
            // Nếu lỗi, hiển thị dữ liệu mặc định
            alert('Không thể tải thông tin giáo viên từ server! Hiển thị dữ liệu mặc định để test.');
            displayTeacherData(defaultTeacher);
        }
    });
}

// Hiển thị dữ liệu lên giao diện
function displayTeacherData(teacher) {
    // Điền thông tin vào thẻ
    $('#cardName').text(teacher.name);
    $('#cardDob').text(teacher.dob);
    $('#cardGender').text(teacher.gender === 'MALE' ? 'Nam' : 'Nữ');
    $('#cardPhone').text(teacher.phone);
    $('#cardEmail').text(teacher.email);
    $('#cardSubject').text(teacher.subjectName || 'Chưa có bộ môn');
    $('#cardUsername').text(teacher.username);

    // Điền thông tin vào form chỉnh sửa
    $('#teacherName').val(teacher.name);
    $('#teacherDob').val(teacher.dob);
    $('#teacherGender').val(teacher.gender);
    $('#teacherPhone').val(teacher.phone);
    $('#teacherEmail').val(teacher.email);
    $('#teacherSubject').val(teacher.subjectName || 'Chưa có bộ môn');
    $('#teacherUsername').val(teacher.username);
}

// Hiển thị/Ẩn form chỉnh sửa
function toggleEditForm() {
    const editForm = $('#editForm');
    if (editForm.is(':visible')) {
        editForm.hide();
        loadTeacherProfile(); // Tải lại dữ liệu để reset form
    } else {
        editForm.show();
    }
}

// Cập nhật thông tin giáo viên
function updateTeacherProfile() {
    const teacherData = {
        name: $('#teacherName').val(),
        dob: $('#teacherDob').val(),
        gender: $('#teacherGender').val(),
        phone: $('#teacherPhone').val(),
        email: $('#teacherEmail').val()
        // Không gửi subjectName và username vì không cho chỉnh sửa
    };

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
            alert('Không thể cập nhật thông tin giáo viên qua server! Cập nhật giao diện với dữ liệu mặc định.');
            displayTeacherData(teacherData); // Cập nhật giao diện với dữ liệu vừa nhập
            toggleEditForm(); // Ẩn form
        }
    });
}

// Tải thông tin khi trang được tải
$(document).ready(function () {
    loadTeacherProfile();
});


// const BASE_URL = 'http://localhost:8080';
//
// // Giả sử userId được lưu trong localStorage sau khi đăng nhập
// const userId = localStorage.getItem('userId') || 1; // Thay bằng logic thực tế để lấy userId
//
// // Tải thông tin giáo viên và hiển thị trên thẻ
// function loadTeacherProfile() {
//     $.ajax({
//         url: `${BASE_URL}/api/teachersProfile/${userId}`,
//         method: 'GET',
//         success: function (teacher) {
//             // Điền thông tin vào thẻ
//             $('#cardName').text(teacher.name);
//             $('#cardDob').text(teacher.dob);
//             $('#cardGender').text(teacher.gender === 'MALE' ? 'Nam' : 'Nữ');
//             $('#cardPhone').text(teacher.phone);
//             $('#cardEmail').text(teacher.email);
//             $('#cardSubject').text(teacher.subjectName || 'Chưa có bộ môn');
//             $('#cardUsername').text(teacher.username);
//
//             // Điền thông tin vào form chỉnh sửa (ẩn ban đầu)
//             $('#teacherName').val(teacher.name);
//             $('#teacherDob').val(teacher.dob);
//             $('#teacherGender').val(teacher.gender);
//             $('#teacherPhone').val(teacher.phone);
//             $('#teacherEmail').val(teacher.email);
//             $('#teacherSubject').val(teacher.subjectName || 'Chưa có bộ môn');
//             $('#teacherUsername').val(teacher.username);
//         },
//         error: function (xhr) {
//             console.error('Error loading teacher profile:', xhr);
//             alert('Không thể tải thông tin giáo viên!');
//         }
//     });
// }
//
// // Hiển thị/Ẩn form chỉnh sửa
// function toggleEditForm() {
//     const editForm = $('#editForm');
//     if (editForm.is(':visible')) {
//         editForm.hide();
//         loadTeacherProfile(); // Tải lại dữ liệu để reset form
//     } else {
//         editForm.show();
//     }
// }
//
// // Cập nhật thông tin giáo viên
// function updateTeacherProfile() {
//     const teacherData = {
//         name: $('#teacherName').val(),
//         dob: $('#teacherDob').val(),
//         gender: $('#teacherGender').val(),
//         phone: $('#teacherPhone').val(),
//         email: $('#teacherEmail').val()
//         // Không gửi subjectName và username vì không cho chỉnh sửa
//     };
//
//     $.ajax({
//         url: `${BASE_URL}/api/teachersProfile/${userId}`,
//         method: 'PUT',
//         contentType: 'application/json',
//         data: JSON.stringify(teacherData),
//         success: function (response) {
//             alert('Cập nhật thông tin giáo viên thành công!');
//             toggleEditForm(); // Ẩn form sau khi lưu
//             loadTeacherProfile(); // Tải lại thông tin để cập nhật thẻ
//         },
//         error: function (xhr) {
//             console.error('Error updating teacher profile:', xhr);
//             alert('Không thể cập nhật thông tin giáo viên: ' + (xhr.responseText || 'Unknown error'));
//         }
//     });
// }
//
// // Tải thông tin khi trang được tải
// $(document).ready(function () {
//     loadTeacherProfile();
// });