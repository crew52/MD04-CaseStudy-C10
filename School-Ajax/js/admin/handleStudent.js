let currentPage = 0;
let pageSize = 10;

// Load initial data
$(document).ready(function() {
    loadStudents(currentPage, pageSize);
});

// Load students
function loadStudents(page, size) {
    $.ajax({
        url: `http://localhost:8080/api/students?page=${page}&size=${size}`,
        method: "GET",
        success: function(response) {
            updateTable(response.content);
            updatePagination(response);
        },
        error: function(xhr, status, error) {
            console.error("Error fetching students: ", error);
            alert("Không thể tải danh sách học sinh. Vui lòng thử lại.");
        }
    });
}

// Update table with student data
function updateTable(students) {
    const tbody = $("#studentTableBody");
    tbody.empty();
    students.forEach(student => {
        const row = `
                <tr>
                    <td>${student.id || ''}</td>
                    <td>${student.name || ''}</td>
                    <td>${student.dob || ''}</td>
                    <td>${student.gender || ''}</td>
                    <td>${student.className || ''}</td>
                    <td>${student.parentContact || ''}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editStudent(${student.id})">
                            <i class="fas fa-edit"></i> Sửa
                        </button>
                        <button class="btn btn-danger btn-sm" onclick="deleteStudent(${student.id})">
                            <i class="fas fa-trash"></i> Xóa
                        </button>
                    </td>
                </tr>
            `;
        tbody.append(row);
    });
}

// Update pagination info
function updatePagination(response) {
    const pageInfo = $("#pageInfo");
    pageInfo.text(`Trang ${response.number + 1} / ${response.totalPages}`);
}

// Change page
function changePage(direction) {
    const totalPages = parseInt($("#pageInfo").text().split("/")[1].trim());
    currentPage += direction;
    if (currentPage < 0) currentPage = 0;
    if (currentPage >= totalPages) currentPage = totalPages - 1;

    loadStudents(currentPage, pageSize);
}



// Save or update student
function saveStudent() {
    const studentData = {
        id: $("#studentId").val() || null,
        name: $("#studentName").val(),
        dob: $("#dob").val(),
        gender: $("#gender").val(),
        classId: $("#classId").val(),
        parentContact: $("#parentContact").val()
    };

    const url = studentData.id ? `http://localhost:8080/api/students/${studentData.id}` : "http://localhost:8080/api/students";
    const method = studentData.id ? "PUT" : "POST";

    $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        data: JSON.stringify(studentData),
        success: function(response) {
            alert(studentData.id ? "Cập nhật thành công!" : "Thêm mới thành công!");
            $("#studentModal").modal('hide'); // Đóng modal sau khi lưu
            clearForm();
            loadStudents(currentPage, pageSize, "", "");
        },
        error: function(xhr, status, error) {
            console.error("Error saving student: ", error);
            alert("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    });
}
//
// // Edit student
// function editStudent(id) {
//     $.ajax({
//         url: `/api/students/${id}`,
//         method: "GET",
//         success: function(student) {
//             $("#studentId").val(student.id);
//             $("#studentName").val(student.name);
//             $("#dob").val(student.dob);
//             $("#gender").val(student.gender);
//             $("#classId").val(student.classId);
//             $("#parentContact").val(student.parentContact);
//             $("#studentModalLabel").text("Cập Nhật Học Sinh");
//         },
//         error: function(xhr, status, error) {
//             console.error("Error fetching student: ", error);
//             alert("Không thể tải thông tin học sinh.");
//         }
//     });
// }
//
// // Delete student
// function deleteStudent(id) {
//     if (confirm("Bạn có chắc chắn muốn xóa học sinh này?")) {
//         $.ajax({
//             url: `/api/students/${id}`,
//             method: "DELETE",
//             success: function() {
//                 alert("Xóa thành công!");
//                 loadStudents(currentPage, pageSize, "", "");
//             },
//             error: function(xhr, status, error) {
//                 console.error("Error deleting student: ", error);
//                 alert("Có lỗi xảy ra khi xóa.");
//             }
//         });
//     }
// }
//
// // Clear form
// function clearForm() {
//     $("#studentId").val("");
//     $("#studentName").val("");
//     $("#dob").val("");
//     $("#gender").val("MALE");
//     $("#classId").val("");
//     $("#parentContact").val("");
// }
