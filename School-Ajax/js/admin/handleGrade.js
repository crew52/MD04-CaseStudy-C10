let currentPage = 0; // Trang hiện tại
const pageSize = 10; // Số phần tử mỗi trang

$(document).ready(function () {
    loadGrades();
});

function loadGrades() {
    let token = getToken();
    if (token == null) {
        window.location.href = "/html/login/login.html";
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: "http://localhost:8080/api/grades",
            method: "GET",
            data: {page: currentPage, size: pageSize},
            success: function (response) {
                console.log("Received grades data:", response);
                renderTable(response.content);
                updatePagination(response);
            },
            error: function (xhr, status, error) {
                console.error("Error loading grades:", status, error);
                alert("Failed to load grades!");
            }
        });
    }
}

function searchGrades() {
    let className = $("#className").val();
    let studentName = $("#studentName").val();
    let subjectName = $("#subjectName").val();
    let examType = $("#examType").val();
    currentPage = 0; // Reset về trang đầu khi tìm kiếm

    let token = getToken();
    if (token == null) {
        window.location.href = "/html/login/login.html";
    } else {
        $.ajax({
            headers: {
                "Authorization": "Bearer " + token,
            },
            url: "http://localhost:8080/api/grades/search",
            method: "GET",
            data: {
                className: className,
                studentName: studentName,
                subjectName: subjectName,
                examType: examType,
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
    let newPage = currentPage + direction;
    if (newPage < 0 || newPage >= totalPages) return; // Không cho vượt giới hạn

    currentPage = newPage;

    let className = $("#className").val().trim();
    let studentName = $("#studentName").val().trim();
    let subjectName = $("#subjectName").val().trim();
    let examType = $("#examType").val().trim();

    if (className || studentName || subjectName || examType) {
        // Nếu có dữ liệu tìm kiếm, gọi searchGrades() nhưng không reset currentPage
        let token = getToken();
        if (token == null) {
            window.location.href = "/html/login/login.html";
        } else {
            $.ajax({
                headers: {
                    "Authorization": "Bearer " + token,
                },
                url: "http://localhost:8080/api/grades/search",
                method: "GET",
                data: {
                    className: className,
                    studentName: studentName,
                    subjectName: subjectName,
                    examType: examType,
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
        }
    } else {
        loadGrades();
    }
}

function renderTable(grades) {
    let tableBody = $("#gradeTableBody");
    tableBody.empty();
    grades.forEach(grade => {
        tableBody.append(`
        <tr>
            <td>${grade.id}</td>
            <td>${grade.studentName}</td>
            <td>${grade.className}</td>
            <td>${grade.subjectName}</td>
            <td>${grade.teacherName}</td>
            <td>${grade.score}</td>
            <td>${grade.examType}</td>
            <td>${grade.date}</td>
            <td>
                <button class="btn btn-warning btn-sm" onclick="editGrade(${grade.id})">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-danger btn-sm" onclick="deleteGrade(${grade.id})">
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

function editGrade(gradeId) {
    let newScore = prompt("Nhập điểm mới:");
    if (newScore !== null && !isNaN(newScore) && newScore >= 0 && newScore <= 10) {
        // Lấy thông tin chi tiết của grade trước
        let token = getToken();
        if (token == null) {
            window.location.href = "/html/login/login.html";
        } else {
            $.ajax({
                headers: {
                    "Authorization": "Bearer " + token,
                },
                url: `http://localhost:8080/api/grades/${gradeId}`,
                method: "GET",
                success: function (grade) {
                    grade.score = newScore; // Cập nhật điểm mới

                    let token = getToken();
                    if (token == null) {
                        window.location.href = "/html/login/login.html";
                    } else {
                        // Gửi lại toàn bộ đối tượng
                        $.ajax({
                            headers: {
                                "Authorization": "Bearer " + token,
                            },
                            url: `http://localhost:8080/api/grades/${gradeId}`,
                            method: "PUT",
                            contentType: "application/json",
                            data: JSON.stringify(grade),
                            success: function () {
                                alert("Cập nhật điểm thành công!");
                                loadGrades(); // Load lại danh sách
                            },
                            error: function () {
                                alert("Lỗi khi cập nhật điểm!");
                            }
                        });
                    }
                },
                error: function () {
                    alert("Lỗi khi lấy dữ liệu điểm số!");
                }
            });
        }
    } else {
        alert("Vui lòng nhập số hợp lệ từ 0 đến 10!");
    }
}


function deleteGrade(gradeId) {
    if (confirm("Bạn có chắc chắn muốn xóa điểm này?")) {
        let token = getToken();
        if (token == null) {
            window.location.href = "/html/login/login.html";
        } else {
            $.ajax({
                headers: {
                    "Authorization": "Bearer " + token,
                },
                url: `http://localhost:8080/api/grades/${gradeId}`,
                method: "DELETE",
                success: function () {
                    alert("Xóa điểm thành công!");
                    loadGrades(); // Load lại danh sách
                },
                error: function () {
                    alert("Lỗi khi xóa điểm!");
                }
            });
        }
    }
}

// viet lay du lieu tu ls
function getToken() {
    let token = localStorage.getItem('token');
    return token;
}