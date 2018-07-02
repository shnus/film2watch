var $image_b64;



$(document).ready(function() {
    $("#cancel").click(function () {
        goToLoginPage();
    });
});

function goToLoginPage() {
    window.location.replace("/login");
}

$(document).ready(function() {
    $("#update").click(function () {
        sendProfile();
    });
});

console.log("ready");

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        console.log(document.getElementById("file").value)
        reader.onload = function (e) {
            $('#image')
                .attr('src', e.target.result)
                .width(300)
                .height(300);
            $image_b64 = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function sendProfile() {
    $('#validation_credentials').empty();
    $('#validation_name').empty();
    var obj = '{'
        +'"login" : "'+$("#login").val()+'",'
        +'"password" : "'+$("#password").val()+'",'
        +'"firstName" : "'+$("#First_name").val()+'",'
        +'"lastName" : "'+$("#Last_name").val()+'",'
        +'"birthDate" : "'+$("#profile_date_year").val()+'-'
        +$("#profile_date_month").val()+'-'+$("#profile_date_day").val()+'",'
        +'"image_b64" : "'+$image_b64+'",'
        +'"location" : "'+$("#location").val()+'",'
        +'"gender" : "'+$("#gender").val()+'"'
        +'}';

    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/logon",
        data: obj,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        headers: headers,
        success: function (data) {
           if(data.credentialsValidation == "INVALID_EMAIL"){
               $('#validation_credentials').prepend(" <div class=\"alert alert-danger\">\n" +
                   "Invalid email\n" +
                   "</div>");
           }

            if(data.credentialsValidation == "USER_EXIST"){
                $('#validation_credentials').prepend(" <div class=\"alert alert-danger\">\n" +
                    "User with this email already exist\n" +
                    "</div>");
            }

            if(data.passwordValidation == "INVALID_PASSWORD"){
                $('#validation_credentials').prepend("<div class=\"alert alert-danger\">\n" +
                    "Invalid password. Should include at least 4 characters\n" +
                    "</div>");
            }

            if(data.nameValidation == "INVALID_NAME"){
                $('#validation_name').prepend("<div class=\"alert alert-danger\">\n" +
                    "Invalid name\n" +
                    "</div>");
            }

            if(data.status == "invalid"){
                alert("Can't create profile. Invalid data.")
            }

            if(data.status == "db problems"){
                alert("Can't create profile. Tech problems.")
            }

            if(data.status == "success"){
                window.location.replace("/login");
            }


        },
        error: function (e) {
            alert("Server problems. Try again later.");
        }
    });

    $image_b64="";
}
