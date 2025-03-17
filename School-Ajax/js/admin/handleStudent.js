function showAll(){
    $.ajax({
        type :" GET",
        url : "http://localhost:8080/api/students",
        success : function (data){
            console.log(data);
        }
    })
}


// function showStudentList() {
//     $('#studentList').removeClass('hidden');
//     $('#studentForm').addClass('hidden');
// }
//
//
// function showAddForm() {
//     $('#studentList').addClass('hidden');
//     $('#studentForm').removeClass('hidden');
//     $('#formTitle').text('Thêm học sinh mới');
//     $('#addEditForm')[0].reset();
//     $('#studentId').val('');
// }
//
// // Hiển thị form sửa học sinh
// function showEditForm(id) {
//     $('#studentList').addClass('hidden');
//     $('#studentForm').removeClass('hidden');
//     $('#formTitle').text('Sửa thông tin học sinh');
//
//     // Giả lập dữ liệu (sẽ thay bằng API sau)
//     const student = {id: id, name: 'Nguyen Van A', dob: '2010-05-15', gender: 'Nam', class: '10A1'};
//     $('#studentId').val(student.id);
//     $('#name').val(student.name);
//     $('#dob').val(student.dob);
//     $('#gender').val(student.gender);
//     $('#class').val(student.class);
// }
//
// // Lưu học sinh (thêm hoặc sửa)
// function saveStudent() {
//     const id = $('#studentId').val();
//     alert(id ? `Đã cập nhật học sinh ID: ${id}` : 'Đã thêm học sinh mới');
//     showStudentList();
// }
//
// // Xóa học sinh
// function deleteStudent(id) {
//     if (confirm(`Bạn có chắc muốn xóa học sinh ID: ${id}?`)) {
//         alert(`Đã xóa học sinh ID: ${id}`);
//     }
// }
//
// // Tìm kiếm học sinh
// function searchStudents() {
//     const searchTerm = $('#searchInput').val().toLowerCase();
//     $('#studentTableBody tr').each(function () {
//         const name = $(this).find('td:eq(1)').text().toLowerCase();
//         $(this).toggle(name.includes(searchTerm));
//     });
// }
