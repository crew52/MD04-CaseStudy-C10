
function login() {
    event.preventDefault();

    let name = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        "username": name,
        "password": password,
    };

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/api/login",
        type: "POST",
        data: JSON.stringify(user),
        success: function (result) {
            localStorage.setItem("token", result.token);
            localStorage.setItem("name", result.name);
            localStorage.setItem("userId", result.id);

            // Lấy role từ authorities
            let role = result.authorities.length > 0 ? result.authorities[0].authority : null;
            localStorage.setItem("role", role);

            // Điều hướng dựa trên role
            if (role === "ROLE_ADMIN") {
                window.location.href = "../../html/admin/pages/attendance/index.html";
            } else if (role === "ROLE_TEACHER") {
                window.location.href = "../../html/teacher/pages/infor/profile.html";
            } else {
                alert("Vai trò không hợp lệ!");
            }
        },
        error: function () {
            alert("Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.");
        }
    });
}

