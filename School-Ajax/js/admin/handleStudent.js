let currentPage = 0;
const pageSize = 10;

// Load tất cả học sinh khi trang được tải
$(document).ready(function () {
    loadAllStudents();
});

// Load tất cả học sinh
function loadAllStudents() {
    $.ajax({
        url: 'http://localhost:8080/api/students',
        method: 'GET',
        success: function (data) {
            displayStudents(data);
            $('#pagination').empty(); // Xóa phân trang khi hiển thị tất cả
        },
        error: function (error) {
            console.error('Error:', error);
            alert('Không thể tải danh sách học sinh!');
        }
    });
}

// Tìm kiếm học sinh
function searchStudents() {
    const name = $('#searchName').val().trim();
    const className = $('#className').val().trim();
    currentPage = 0; // Reset về trang đầu tiên khi tìm kiếm

    let url = '';
    if (name && className) {
        url = `http://localhost:8080/api/students/search/by-name-and-class?name=${name}&className=${className}&page=${currentPage}`;
    } else if (name) {
        url = `http://localhost:8080/api/students/search/paginated?name=${name}&page=${currentPage}`;
    } else {
        alert('Vui lòng nhập tên để tìm kiếm!');
        return;
    }

    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            displayStudents(data.content);
            renderPagination(data.totalPages, data.number);
        },
        error: function (error) {
            console.error('Error:', error);
            alert('Không tìm thấy học sinh!');
        }
    });
}

// Hiển thị danh sách học sinh
function displayStudents(students) {
    const tbody = $('#studentTableBody');
    tbody.empty();

    students.forEach(student => {
        const row = `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.dob}</td>
                <td>${student.gender}</td>
                <td>${student.classEntity ? student.classEntity.className : 'N/A'}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="showEditForm(${student.id})">
                        <i class="fa fa-edit"></i> Sửa
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteStudent(${student.id})">
                        <i class="fa fa-trash"></i> Xóa
                    </button>
                </td>
            </tr>
        `;
        tbody.append(row);
    });
}

// Render phân trang
function renderPagination(totalPages, currentPageNumber) {
    const pagination = $('#pagination');
    pagination.empty();

    // Nút Previous
    pagination.append(`
        <li class="page-item ${currentPageNumber === 0 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="changePage(${currentPage - 1})">Trước</a>
        </li>
    `);

    // Số trang
    for (let i = 0; i < totalPages; i++) {
        pagination.append(`
            <li class="page-item ${i === currentPageNumber ? 'active' : ''}">
                <a class="page-link" href="#" onclick="changePage(${i})">${i + 1}</a>
            </li>
        `);
    }

    // Nút Next
    pagination.append(`
        <li class="page-item ${currentPageNumber === totalPages - 1 ? 'disabled' : ''}">
            <a class="page-link" href="#" onclick="changePage(${currentPage + 1})">Tiếp</a>
        </li>
    `);
}

// Chuyển trang
function changePage(page) {
    if (page >= 0) {
        currentPage = page;
        searchStudents();
    }
}

// Hiển thị form thêm học sinh
function showAddForm() {
    $('#formTitle').text('Thêm học sinh mới');
    $('#addEditForm')[0].reset();
    $('#studentId').val('');
    $('#studentList').addClass('hidden');
    $('#studentForm').removeClass('hidden');
}

// Hiển thị form sửa học sinh
function showEditForm(id) {
    $.ajax({
        url: `http://localhost:8080/api/students/${id}`,
        method: 'GET',
        success: function (student) {
            $('#formTitle').text('Sửa thông tin học sinh');
            $('#studentId').val(student.id);
            $('#name').val(student.name);
            $('#dob').val(student.dob);
            $('#gender').val(student.gender === 'MALE' ? 'Nam' : 'Nữ');
            $('#classIdInput').val(student.classEntity ? student.classEntity.id : '');
            $('#studentList').addClass('hidden');
            $('#studentForm').removeClass('hidden');
        },
        error: function (error) {
            console.error('Error:', error);
            alert('Không thể tải thông tin học sinh!');
        }
    });
}

// Hiển thị danh sách học sinh
function showStudentList() {
    $('#studentForm').addClass('hidden');
    $('#studentList').removeClass('hidden');
    loadAllStudents();
}

// Lưu học sinh (thêm hoặc sửa)
function saveStudent() {
    const student = {
        id: $('#studentId').val() ? parseInt($('#studentId').val()) : null,
        name: $('#name').val(),
        dob: $('#dob').val(),
        gender: $('#gender').val() === 'Nam' ? 'MALE' : 'FEMALE',
        classEntity: { id: parseInt($('#classIdInput').val()) }
    };

    const url = student.id ? `http://localhost:8080/api/students/${student.id}` : 'http://localhost:8080/api/students';
    const method = student.id ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(student),
        success: function () {
            alert(student.id ? 'Cập nhật học sinh thành công!' : 'Thêm học sinh thành công!');
            showStudentList();
        },
        error: function (error) {
            console.error('Error:', error);
            alert('Lỗi khi lưu học sinh!');
        }
    });
}

// Xóa học sinh
function deleteStudent(id) {
    if (confirm('Bạn có chắc muốn xóa học sinh này?')) {
        $.ajax({
            url: `http://localhost:8080/api/students/${id}`,
            method: 'DELETE',
            success: function () {
                alert('Xóa học sinh thành công!');
                loadAllStudents();
            },
            error: function (error) {
                console.error('Error:', error);
                alert('Lỗi khi xóa học sinh!');
            }
        });
    }
}

