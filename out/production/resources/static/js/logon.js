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

    alert(obj);

    $.ajax({
        type: "POST",
        url: "logon",
        contentType: "application/json",
        data: obj,
        success: function (msg, status, jqXHR) {
            document.open();
            document.write(msg);
            document.close();
        }
    });

    $image_b64="";
}