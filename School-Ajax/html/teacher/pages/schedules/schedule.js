$(document).ready(function () {
    function loadTeacherNames() {
        $.ajax({
            url: 'http://localhost:8080/api/teachers/names',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#teacher-id-input').autocomplete({
                    source: data
                });
            },
            error: function (error) {
                console.error('Có lỗi xảy ra khi tải danh sách tên giáo viên:', error);
            }
        });
    }

    loadTeacherNames();

    function loadScheduleForTeacher(teacherName) {
        $.ajax({
            url: `http://localhost:8080/schedules/teacher/name/${teacherName}`,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                var tableBody = $('table tbody');
                tableBody.empty();

                if (data.length === 0) {
                    tableBody.append('<tr><td colspan="8">Không tìm thấy dữ liệu.</td></tr>');
                } else {
                    data.forEach(function (item) {
                        var row = `
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.classEntity ? item.classEntity.className : 'Không có dữ liệu'}</td>
                                <td>${item.subject ? item.subject.subjectName : 'Không có dữ liệu'}</td>
                                <td>${item.teacher ? item.teacher.name : 'Không có dữ liệu'}</td>
                                <td>${item.dayOfWeek}</td>
                                <td>${item.date}</td>
                                <td>${item.startTime}</td>
                                <td>${item.endTime}</td>
                            </tr>
                        `;
                        tableBody.append(row);
                    });
                }
            },
            error: function (error) {
                console.error('Có lỗi xảy ra khi tải dữ liệu:', error);
                var tableBody = $('table tbody');
                tableBody.empty();
                tableBody.append('<tr><td colspan="8">Lỗi khi tải dữ liệu. Vui lòng thử lại sau.</td></tr>');
            }
        });
    }

    $('#search-button').click(function () {
        var teacherName = $('#teacher-id-input').val();
        if (teacherName) {
            loadScheduleForTeacher(teacherName);
        } else {
            alert('Vui lòng nhập tên Giáo viên!');
        }
    });
});