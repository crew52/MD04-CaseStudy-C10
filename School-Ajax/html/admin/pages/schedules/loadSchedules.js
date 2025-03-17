$(document).ready(function () {
    loadSchedules();
});

function loadSchedules() {
    $.ajax({
        url: "http://localhost:8080/schedules",  // API lấy danh sách lịch học
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log("Dữ liệu từ API:", data); // Kiểm tra dữ liệu trả về
            let content = "";
            data.forEach((schedule, index) => {
                console.log("Lịch học:", schedule); // Kiểm tra từng phần tử
                content += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${schedule.classEntity?.className || "N/A"}</td>
                        <td>${schedule.subject?.subjectName || "N/A"}</td>
                        <td>${schedule.teacher?.name || "N/A"}</td>
                        <td>${schedule.dayOfWeek} (${schedule.startTime} - ${schedule.endTime}</td>
                    </tr>`;
            });
            $("#scheduleTable").html(content);
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu:", error);
        }
    });
}