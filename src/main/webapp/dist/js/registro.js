let url = serverurl;


$(document).ready(function () {
    $("#registform").submit(function (e) {
        e.preventDefault();
    });

    $("#agreeTerms").click(function () {
        $("#registro").attr("disabled", !this.checked);
    });

    $("#registro").click(function () {

        let nom = $("#nombre").val();
        let apel = $("#apellido").val();
        let correo = $("#correo").val();
        let tel = $("#telefono").val();
        let doc = $("#documento").val();
        let pass = $("#contraseña").val();
        //let confpass = $("#confcontraseña").val();
        let ed = $("#edad").val();




        let loadurl = url + 'user/registro';
        //let url = 'http://localhost:8080/user/registro';
        let auth = {};
        let data = {
            id: '',
            nombre: nom,
            apellido: apel,
            email: correo,
            telefono: tel,
            documento: doc,
            contraseña: pass,
            rol: 2,
            edad: ed
        };
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

                        console.log(data);

                        if (data.email == correo) {
                            alert("registro exitoso");

                            location.href = "./login.html";
                        }

                        if (data.msg == "usuario ya existe") {
                            alert("ya hay un usuario con este correo");
                        }


                    }
                });


    });




});