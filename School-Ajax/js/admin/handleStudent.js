let currentPage = 0;
let pageSize = 10;
let currentSearchParams = null;

// Load initial data
$(document).ready(function() {
    loadStudents(currentPage, pageSize);
    loadClasses(); // Tải danh sách lớp cho modal thêm/sửa
    loadSearchClasses(); // Tải danh sách lớp cho modal tìm kiếm
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

// Load classes into select box for adding/editing
function loadClasses() {
    $.ajax({
        url: "http://localhost:8080/api/classes",
        method: "GET",
        success: function(response) {
            const classSelect = $("#classId");
            classSelect.empty();
            classSelect.append('<option value="">Chọn lớp</option>');
            response.forEach(cls => {
                classSelect.append(`<option value="${cls.id}">${cls.className}</option>`);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error fetching classes: ", error);
            alert("Không thể tải danh sách lớp học. Vui lòng thử lại.");
        }
    });
}

// Load classes into select box for searching
function loadSearchClasses() {
    $.ajax({
        url: "http://localhost:8080/api/classes",
        method: "GET",
        success: function(response) {
            const searchClassSelect = $("#searchClassId");
            searchClassSelect.empty();
            searchClassSelect.append('<option value="">Tất cả lớp</option>');
            response.forEach(cls => {
                searchClassSelect.append(`<option value="${cls.id}">${cls.className}</option>`);
            });
        },
        error: function(xhr, status, error) {
            console.error("Error fetching classes: ", error);
            alert("Không thể tải danh sách lớp học cho tìm kiếm. Vui lòng thử lại.");
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

    if (currentSearchParams) {
        searchStudentsWithParams(currentPage, pageSize, currentSearchParams);
    } else {
        loadStudents(currentPage, pageSize);
    }
}

// Prepare form for adding new student
function prepareAddStudent() {
    clearForm();
    $("#studentModalLabel").text("Thêm Học Sinh");
}

// Clear form
function clearForm() {
    $("#studentForm")[0].reset();
    $("#studentId").val("");
}

// Edit student
function editStudent(id) {
    $.ajax({
        url: `http://localhost:8080/api/students/${id}`,
        method: "GET",
        success: function(student) {
            $("#studentId").val(student.id);
            $("#studentName").val(student.name);
            $("#dob").val(student.dob);
            $("#gender").val(student.gender);
            $("#classId").val(student.classId);
            $("#parentContact").val(student.parentContact);
            $("#studentModalLabel").text("Cập Nhật Học Sinh");
            $("#studentModal").modal('show');
        },
        error: function(xhr, status, error) {
            console.error("Error fetching student: ", error);
            alert("Không thể tải thông tin học sinh. Vui lòng thử lại.");
        }
    });
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
            $("#studentModal").modal('hide');
            clearForm();
            loadStudents(currentPage, pageSize);
        },
        error: function(xhr, status, error) {
            console.error("Error saving student: ", error);
            alert("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    });
}

// Delete student
function deleteStudent(id) {
    if (confirm("Bạn có chắc chắn muốn xóa học sinh này?")) {
        $.ajax({
            url: `http://localhost:8080/api/students/${id}`,
            method: "DELETE",
            success: function() {
                alert("Xóa thành công!");
                loadStudents(currentPage, pageSize);
            },
            error: function(xhr, status, error) {
                console.error("Error deleting student: ", error);
                alert("Không thể xóa học sinh. Vui lòng thử lại.");
            }
        });
    }
}

// Search students
function searchStudents() {
    const searchName = $("#searchName").val().trim();
    const searchClassId = $("#searchClassId").val(); // Lấy classId từ dropdown

    currentSearchParams = null;
    currentPage = 0; // Reset về trang đầu khi tìm kiếm mới

    // Chỉ gửi yêu cầu nếu có ít nhất một tiêu chí tìm kiếm
    if (searchName || (searchClassId && searchClassId !== "")) {
        currentSearchParams = {
            name: searchName || null,
            classId: searchClassId || null
        };
        searchStudentsWithParams(currentPage, pageSize, currentSearchParams);
    } else {
        loadStudents(currentPage, pageSize); // Tải danh sách mặc định nếu không có tiêu chí
    }

    $("#searchModal").modal('hide');
}

// Helper function to search with params
function searchStudentsWithParams(page, size, params) {
    let url = "http://localhost:8080/api/students";
    let queryParams = `?page=${page}&size=${size}`;

    if (params.name && params.classId) {
        queryParams += `&name=${encodeURIComponent(params.name)}&classId=${encodeURIComponent(params.classId)}`;
        url += "/search" + queryParams; // Gửi cả name và classId
    } else if (params.name) {
        queryParams += `&name=${encodeURIComponent(params.name)}`;
        url += "/search/name" + queryParams;
    } else if (params.classId) {
        queryParams += `&classId=${encodeURIComponent(params.classId)}`; // Gửi classId
        url += "/search/class" + queryParams;
    }

    $.ajax({
        url: url,
        method: "GET",
        success: function(response) {
            updateTable(response.content);
            updatePagination(response);
        },
        error: function(xhr, status, error) {
            console.error("Error searching students: ", xhr.responseText || error);
            alert("Không thể tìm kiếm học sinh. Vui lòng kiểm tra lại tiêu chí hoặc thử lại sau.");
        }
    });

}
