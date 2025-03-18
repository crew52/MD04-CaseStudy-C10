$(document).ready(function () {
    loadSchedules();
});

// Load danh sách lịch học
function loadSchedules() {
    $.ajax({
        url: "http://localhost:8080/schedules",
        type: "GET",
        dataType: "json",
        success: function (data) {
            let content = "";
            data.forEach((schedule, index) => {
                content += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${schedule.classEntity?.className || "N/A"}</td>
                        <td>${schedule.subject?.subjectName || "N/A"}</td>
                        <td>${schedule.teacher?.name || "N/A"}</td>
                        <td>${schedule.dayOfWeek} (${schedule.startTime} - ${schedule.endTime})</td>
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="editSchedule(${schedule.id})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteSchedule(${schedule.id})">Xóa</button>
                        </td>
                    </tr>`;
            });
            $("#scheduleTable").html(content);
        },
        error: function () {
            console.error("Không thể lấy dữ liệu.");
        }
    });
}

// Thêm hoặc cập nhật lịch học
function saveSchedule() {
    let id = $("#scheduleId").val();
    let scheduleData = {
        classEntity: { className: $("#className").val() },
        subject: { subjectName: $("#subjectName").val() },
        teacher: { name: $("#teacherName").val() },
        dayOfWeek: $("#dayOfWeek").val()
    };

    let method = id ? "PUT" : "POST";
    let url = id ? `http://localhost:8080/schedules/${id}` : "http://localhost:8080/schedules";

    $.ajax({
        url: url,
        type: method,
        contentType: "application/json",
        data: JSON.stringify(scheduleData),
        success: function () {
            alert(id ? "Cập nhật thành công!" : "Thêm mới thành công!");
            $("#scheduleId").val("");
            $("#formTitle").text("Thêm Lịch Học");
            loadSchedules();
        },
        error: function () {
            alert("Lỗi khi lưu lịch học.");
        }
    });
}

// Sửa lịch học
function editSchedule(id) {
    $.ajax({
        url: `http://localhost:8080/schedules/${id}`,
        type: "GET",
        success: function (data) {
            $("#scheduleId").val(data.id);
            $("#className").val(data.classEntity?.className || "");
            $("#subjectName").val(data.subject?.subjectName || "");
            $("#teacherName").val(data.teacher?.name || "");
            $("#dayOfWeek").val(data.dayOfWeek || "");
            $("#formTitle").text("Cập nhật Lịch Học");
        },
        error: function () {
            alert("Lỗi khi tải lịch học.");
        }
    });
}

// Xóa lịch học
function deleteSchedule(id) {
    if (confirm("Bạn có chắc chắn muốn xóa?")) {
        $.ajax({
            url: `http://localhost:8080/schedules/${id}`,
            type: "DELETE",
            success: function () {
                alert("Xóa thành công!");
                loadSchedules();
            },
            error: function () {
                alert("Lỗi khi xóa.");
            }
        });
    }
}
