let currentPage = 0; // Trang hiện tại
const pageSize = 10; // Số phần tử mỗi trang

$(document).ready(function () {
    loadAttendances();
});

function loadAttendances() {
    // let url;
    let token = getToken();
    console.log(token);
    if (token == null) {
        window.location.href = "/html/login/login.html";
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: "http://localhost:8080/api/attendances",
            method: "GET",
            data: {page: currentPage, size: pageSize},
            success: function (response) {
                console.log("Received attendance data:", response);
                renderTable(response.content);
                updatePagination(response);
            },
            error: function (xhr, status, error) {
                console.error("Error loading attendances:", status, error);
                alert("Failed to attendance grades!");
            }
        });
    }
}

function renderTable(attendances) {
    let tableBody = $("#attendanceTableBody");
    tableBody.empty();
    attendances.forEach(attendance => {
        tableBody.append(`
        <tr>
            <td>${attendance.id}</td>
            <td>${attendance.studentName}</td>
            <td>${attendance.className}</td>
            <td>${attendance.subjectName}</td>
            <td>${attendance.teacherName}</td>
            <td>${attendance.status}</td>
            <td>${attendance.date}</td>
            <td>${attendance.startTime} ${attendance.endTime}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editAttendance(${attendance.id})">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteAttendance(${attendance.id})">
                    <i class="fas fa-trash-alt"></i> Delete
                </button>
            </td>
        </tr>
        `);
    });
}

function updatePagination(response) {
    totalPages = response.totalPages; // Cập nhật totalPages từ response
    $("#currentPage").text(`Page ${currentPage + 1} of ${totalPages}`);
    $("#prevPage").prop("disabled", currentPage === 0);
    $("#nextPage").prop("disabled", currentPage >= totalPages - 1);
}

function deleteAttendance(attendanceId) {
    // let url;
    let token = getToken();
    console.log(token);
    if (token == null) {
        window.location.href = "/html/login/login.html";
    } else {
        if (confirm("Bạn có chắc chắn muốn xóa điểm danh này?")) {
            $.ajax({
                headers: {
                    "Authorization": "Bearer " + token,
                },
                url: `http://localhost:8080/api/attendances/${attendanceId}`,
                method: "DELETE",
                success: function () {
                    alert("Xóa điểm thành công!");
                    loadAttendances(); // Load lại danh sách
                },
                error: function () {
                    alert("Lỗi khi xóa điểm danh!");
                }
            });
        }
    }
}

function editAttendance(attendanceId) {
    // let url;
    let token = getToken();
    console.log(token);
    if (token==null) {
        window.location.href = "/html/login/login.html";
    }
    else {
        Swal.fire({
            title: "Chọn trạng thái mới",
            input: "select",
            inputOptions: {
                PRESENT: "PRESENT",
                ABSENT: "ABSENT",
                LATE: "LATE"
            },
            inputPlaceholder: "Chọn trạng thái",
            showCancelButton: true,
            confirmButtonText: "Cập nhật",
            cancelButtonText: "Hủy"
        }).then((result) => {
            if (result.isConfirmed && result.value) {
                let newStatus = result.value;

                $.ajax({
                    headers: {
                        "Authorization": "Bearer " + token,
                    },
                    url: `http://localhost:8080/api/attendances/${attendanceId}`,
                    method: "GET",
                    success: function (attendance) {
                        attendance.status = newStatus; // Cập nhật trạng thái mới

                        // Gửi lại toàn bộ đối tượng
                        $.ajax({
                            headers: {
                                "Authorization": "Bearer " + token,
                            },
                            url: `http://localhost:8080/api/attendances/${attendanceId}`,
                            method: "PUT",
                            contentType: "application/json",
                            data: JSON.stringify(attendance),
                            success: function () {
                                Swal.fire("Thành công!", "Cập nhật điểm danh thành công!", "success");
                                loadAttendances(); // Load lại danh sách
                            },
                            error: function () {
                                Swal.fire("Lỗi!", "Lỗi khi cập nhật điểm danh!", "error");
                            }
                        });
                    },
                    error: function () {
                        Swal.fire("Lỗi!", "Lỗi khi lấy dữ liệu điểm danh!", "error");
                    }
                });
            }
        });
    }
}

function searchAttendances() {
    let className = $("#className").val();
    let studentName = $("#studentName").val();
    currentPage = 0; // Reset về trang đầu khi tìm kiếm

    // let url;
    let token = getToken();
    console.log(token);
    if (token == null) {
        window.location.href = "/html/login/login.html";
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: "http://localhost:8080/api/attendances/search",
            method: "GET",
            data: {
                className: className,
                studentName: studentName,
                page: currentPage,
                size: pageSize
            },
            success: function (response) {
                console.log("Search result:", response);
                totalPages = response.totalPages; // Cập nhật totalPages
                renderTable(response.content);
                updatePagination(response);
            },
            error: function (xhr, status, error) {
                console.error("Search error:", status, error);
                alert("Failed to search grades!");
            }
        });
    }
}


function changePage(direction) {
    // let url;
    let token = getToken();
    console.log(token);
    if (token==null) {
        window.location.href = "/html/login/login.html";
    }
    else {
        let newPage = currentPage + direction;
        if (newPage < 0 || newPage >= totalPages) return; // Không cho vượt giới hạn

        currentPage = newPage;

        let className = $("#className").val().trim();
        let studentName = $("#studentName").val().trim();

        if (className || studentName) {
            // Nếu có dữ liệu tìm kiếm, gọi searchGrades() nhưng không reset currentPage
            $.ajax({
                headers: {
                    "Authorization": "Bearer " + token,
                },
                url: "http://localhost:8080/api/attendances/search",
                method: "GET",
                data: {
                    className: className,
                    studentName: studentName,
                    page: currentPage,
                    size: pageSize
                },
                success: function (response) {
                    console.log("Pagination search result:", response);
                    totalPages = response.totalPages; // Cập nhật totalPages
                    renderTable(response.content);
                    updatePagination(response);
                },
                error: function (xhr, status, error) {
                    console.error("Pagination search error:", status, error);
                    alert("Failed to load search results!");
                }
            });
        } else {
            loadAttendances();
        }
    }
}

// viet lay du lieu tu ls
function getToken() {
    let token = localStorage.getItem('token');
    return token;
}

// phai dang nhap moi co token
// localStorage.setItem("token", token)

function getName() {
    let name = localStorage.getItem('name');
    document.getElementById("name").innerText = name;
}

getName()

