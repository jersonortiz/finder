let url = serverurl;

$(document).ready(function () {
    $("#loginform").submit(function (e) {
        e.preventDefault();
    });

    $("#login").click(function () {

        let email = $("#email").val();
        let password = $("#contraseña").val();

        let loadurl = url + 'user/login';
        //let url = 'http://localhost:8080/user/login';
        let auth = {};
        let data = {email: email, contraseña: password};
        console.log(data);


        let init = {
            method: 'POST',
            headers: {
                mode: 'cors',
                'Accept': 'application/json',
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            },
            body: JSON.stringify(data),
            cache: 'default'
        };

        fetch(loadurl, init)
                .then((resp) => resp.json())
                .then(function (data) {
                    if (data) {

                        sessionStorage.setItem("USER_SESSION", JSON.stringify(data));

                        console.log(data);
                        console.log(data.rol);

                        if (data.rol == 1) {
                            location.href = "./admin/dashboard.html";

                        }

                        if (data.rol == 2) {
                            location.href = "./user/dashboard.html";

                        }
                        if (data.rol == 3) {
                            location.href = "./profesional/dashboard.html";

                        }
                        console.log(data.rol);
                    }
                });

    });

});