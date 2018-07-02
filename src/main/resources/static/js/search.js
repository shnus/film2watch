$(document).ready(function() {
    $("#search").click(function () {
        $("#results").empty();
        search();
    });
});

var div;
var curButton;
$(document).on('click', "#addToWatchlist", function () {
    curButton = $(this);
    var parents = $(this).parents();
    div = parents[0];
    var num = div.children[0].value;

    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    var obj = '{'
        +'"id" : "'+films[num].id+'",'
        +'"vote_average" : "'+films[num].vote_average+'",'
        +'"vote" : "'+0+'",'
        +'"title" : "'+films[num].title +'",'
        +'"poster_path" : "'+films[num].poster_path +'",'
        +'"original_language" : "'+films[num].original_language +'",'
        +'"overview" : "'+films[num].overview +'",'
        +'"release_date" : "'+films[num].release_date+'"'
        +'}';

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/addToWatchlist",
        data: obj,
        dataType: 'json',
        cache: false,
        timeout: 6000,
        headers: headers,
        success: function (data) {
            if(data.status === "success"){
                curButton.addClass("btn-success");
                curButton.html("Successfully added");
            } else {
                curButton.addClass("btn-danger");
                curButton.html("Already in watchlist");
            }
        },
        error: function (e) {
            alert("Server problems. Try again later.");
        }
    });
});

    var films;
function search() {
    var lang = "${sessionScope.locale}"
    var surl = "https://api.themoviedb.org/3/search/movie?api_key=d5fbdafc1e8b3130160515b181cd55be&language="+lang+"-US&query="+encodeURI($("#text").val())+"&page=1&include_adult=false";
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": surl,
        "method": "GET",
        "headers": {},
        "data": "{}"
    }
    console.log(surl);


    $.ajax(settings).done(function (response) {
        console.log(response);
        var resp = response;
        films = resp.results;
        if(films.length==0){
            $("#results").append("No such movie in database");
        }else {
            for (var i = 0; i < films.length; i++) {
                if(films[i].poster_path==null)
                    continue;
                var title = films[i].title;
                var overview = films[i].overview;
                var release_date = films[i].release_date;
                var origignal_language = films[i].original_language;
                var poster_path = "https://image.tmdb.org/t/p/w500" + films[i].poster_path;
                var vote_average = films[i].vote_average;

                var rate = Math.round(Number(vote_average));
                var j;
                if(i%2==0){
                    j='F';
                } else {
                    j='E'
                }
                $("#results").append('' +

                    '<article style="background: #'+j+j+j+'">' +
                    '<article style="margin: 50px" class="search-result row">' +
                    '<div class="col-xs-12 col-sm-12 col-md-3">' +
                    '<a href="#" class="thumbnail"><img ' +
                    'src="' + poster_path + '"/></a> ' +
                    ' </div> ' +
                    '<div class="col-xs-12 col-sm-12 col-md-2"> ' +
                    '<ul class="meta-search">' +
                    '<li><span>Release date: ' + release_date + '</span></li>' +
                    '<li><span>Original language: ' + origignal_language + '</span></li>' +
                    '<li></i> <span>Average score: ' + vote_average + '</span></li>' +
                   // '<li></i> <span>Rate the movie:</span></li>' +
                   // '<li></i> <span><input type="number" name="your_awesome_parameter" id="some_id" class="rating" data-max="10" data-min="1" value="'+rate+'"/></span></li>' +
                    '</ul>' +
                    '</div>' +
                    '<div class="col-xs-12 col-sm-12 col-md-7 excerpet">' +
                    '   <h3>' + title + '</h3>' +
                    ' <p>' + overview + '</p>' +
                    '<div style="align: right" class="plus">' +
                    '<input type="hidden" value="'+i+'">'+
                    '<button class="btn btn-de" id="addToWatchlist" title="Add to watchlist">Add to watchlist</button></div>' +
                    '</div>' +
                    '<span class="clearfix borda"></span>' +
                    '</article>' +
                    '</article>');
            }
            $.getScript('static/js/stars.js');
        }
    });
}
