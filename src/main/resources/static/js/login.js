function get(name){
    if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}

$(document).ready(function(){
    var error = get("error")
    if(error=="true"){
        $('#login_div').prepend('<div class="alert alert-danger">\n' +
            'Incorrect email or password' +
            '</div>')
    }

})