function login() {
    event.preventDefault();
//    lay du lieu
    let name = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let user={
        "username": name,
        "password": password,
    }
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/api/login",
        type: "POST",
        data: JSON.stringify(user),
        success: function (result) {
            // console.log(result);
            localStorage.setItem("token", result.token);
            localStorage.setItem("name",result.name);
            window.location.href = "../../html/admin/pages/attendance/index.html";
        }
    })
}