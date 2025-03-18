const BASE_URL = 'http://localhost:8080/api/teachers';
let currentPage = 0;
const pageSize = 10;
let isSearching = false;
let currentSearchName = '';
let currentSearchSubject = '';

$(document).ready(function () {
    loadTeachers(currentPage);
});

function loadTeachers(page) {
    currentPage = page;
    isSearching = false;
    currentSearchName = '';
    currentSearchSubject = '';
    $.ajax({
        url: `${BASE_URL}?page=${page}&size=${pageSize}`,
        method: 'GET',
        success: function (data) {
            renderTeachers(data);
        },
        error: function () {
            alert('Error loading teachers');
        }
    });
}

function searchTeachers() {
    const name = $('#searchName').val().trim();
    const subject = $('#searchSubject').val();
    currentSearchName = name;
    currentSearchSubject = subject;
    isSearching = name || subject; // Có ít nhất một điều kiện tìm kiếm
    currentPage = 0; // Reset về trang đầu khi tìm kiếm

    let url = `${BASE_URL}/search?page=${currentPage}&size=${pageSize}`;
    if (name) url += `&name=${encodeURIComponent(name)}`;
    if (subject) url += `&subjectName=${encodeURIComponent(subject)}`;

    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            renderTeachers(data);
        },
        error: function () {
            alert('Error searching teachers');
        }
    });
}

function searchTeachersByPage(page) {
    currentPage = page;
    let url = `${BASE_URL}/search?page=${page}&size=${pageSize}`;
    if (currentSearchName) url += `&name=${encodeURIComponent(currentSearchName)}`;
    if (currentSearchSubject) url += `&subjectName=${encodeURIComponent(currentSearchSubject)}`;

    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            renderTeachers(data);
        },
        error: function () {
            alert('Error searching teachers');
        }
    });
}

function renderTeachers(data) {
    const tbody = $('#teacherTableBody');
    tbody.empty();
    data.content.forEach(teacher => {
        const userInfo = teacher.user ? teacher.user.username : 'None';
        const row = `
            <tr>
                <td>${teacher.id}</td>
                <td>${teacher.name}</td>
                <td>${teacher.dob}</td>
                <td>${teacher.gender}</td>
                <td>${teacher.email}</td>
                <td>${teacher.phone}</td>
                <td>${teacher.subject ? teacher.subject.subjectName : 'None'}</td>
                <td>${userInfo}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="openEditModal(${teacher.id})">Edit</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteTeacher(${teacher.id})">Delete</button>
                    ${!teacher.user ? `<button class="btn btn-sm btn-success" onclick="openUserModal(${teacher.id})">Create User</button>` : ''}
                </td>
            </tr>`;
        tbody.append(row);
    });
    updatePagination(data);
}

function updatePagination(data) {
    const pagination = $('#pagination');
    pagination.empty();
    if (data.totalPages > 1) {
        for (let i = 0; i < data.totalPages; i++) {
            const active = i === data.number ? 'active' : '';
            const onclickFunc = isSearching ? `searchTeachersByPage(${i})` : `loadTeachers(${i})`;
            pagination.append(`<li class="page-item ${active}"><a class="page-link" href="#" onclick="${onclickFunc}">${i + 1}</a></li>`);
        }
    }
}

function openAddModal() {
    $('#teacherModalLabel').text('Add New Teacher');
    $('#teacherForm')[0].reset();
    $('#teacherId').val('');
    $('#teacherModal').modal('show');
}

function openEditModal(id) {
    $.ajax({
        url: `${BASE_URL}/${id}`,
        method: 'GET',
        success: function (teacher) {
            $('#teacherModalLabel').text('Edit Teacher');
            $('#teacherId').val(teacher.id);
            $('#name').val(teacher.name);
            $('#dob').val(teacher.dob);
            $('#gender').val(teacher.gender);
            $('#email').val(teacher.email);
            $('#phone').val(teacher.phone);
            $('#subject').val(teacher.subject ? teacher.subject.id : '');
            $('#teacherModal').modal('show');
        },
        error: function () {
            alert('Error fetching teacher');
        }
    });
}

function saveTeacher() {
    const id = $('#teacherId').val();
    const teacher = {
        name: $('#name').val(),
        dob: $('#dob').val(),
        gender: $('#gender').val(),
        email: $('#email').val(),
        phone: $('#phone').val(),
        subject: { id: parseInt($('#subject').val()), subjectName: $('#subject option:selected').text() }
    };
    const url = id ? `${BASE_URL}/${id}` : BASE_URL;
    const method = id ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(teacher),
        success: function () {
            $('#teacherModal').modal('hide');
            loadTeachers(currentPage);
        },
        error: function (xhr) {
            alert('Error saving teacher: ' + xhr.responseText);
        }
    });
}

function deleteTeacher(id) {
    if (confirm('Are you sure you want to delete this teacher?')) {
        $.ajax({
            url: `${BASE_URL}/${id}`,
            method: 'DELETE',
            success: function () {
                loadTeachers(currentPage);
            },
            error: function () {
                alert('Error deleting teacher');
            }
        });
    }
}

function openUserModal(teacherId) {
    $('#teacherIdForUser').val(teacherId);
    $('#userForm')[0].reset();
    $('#userModal').modal('show');
}

function resetSearch() {
    $('#searchName').val('');
    $('#searchSubject').val('');
    loadTeachers(0);
}

function createUserForTeacher() {
    const teacherId = $('#teacherIdForUser').val();
    $.ajax({
        url: `${BASE_URL}/${teacherId}`,
        method: 'GET',
        success: function (teacher) {
            const user = {
                username: $('#username').val(),
                password: $('#password').val(),
                role: { id: parseInt($('#role').val()) }
            };
            const request = {
                teacher: teacher,
                user: user
            };

            $.ajax({
                url: `${BASE_URL}/create-with-user`,
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(request),
                success: function () {
                    $('#userModal').modal('hide');
                    loadTeachers(currentPage);
                },
                error: function (xhr) {
                    alert('Error creating user: ' + xhr.responseText);
                }
            });
        },
        error: function () {
            alert('Error fetching teacher for user creation');
        }
    });
}