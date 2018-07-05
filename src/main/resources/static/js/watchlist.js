$(document).ready(function() {
    getWatchlist();
});

var i;
var span;
var films;
function getWatchlist() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/getWatchlist",
        dataType: 'json',
        cache: false,
        timeout: 6000,
        headers: headers,
        success: function (data) {
            films = data;
            if(films.length>0){
                var j;
                for(i = 0; i < films.length; i ++){
                    j=films.length-1-i;
                    if(films[j].poster_path==null)
                        continue;
                    var film_num = String(j);
                    var title = films[j].title;
                    var overview = films[j].overview;
                    var release_date = films[j].release_date;
                    var origignal_language = films[j].original_language;
                    var poster_path = "https://image.tmdb.org/t/p/w500" + films[j].poster_path;
                    var vote_average = films[j].vote_average;
                    var vote = films[j].vote;
                    var stars_name = "rating_"+i;
                    if(vote==-1){
                        vote = "film not rated"
                    }
                    var rate = Math.round(Number(vote_average));
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
                        '<input type="hidden" value="'+film_num+'">'+
                        '<ul class="meta-search">' +
                        '<li><span>Release date: ' + release_date + '</span></li>' +
                        '<li><span>Original language: ' + origignal_language + '</span></li>' +
                        '<li><span>Average score: ' + vote_average + '</span></li>' +
                        '<li><span>Your rating: ' + vote + '</span></li>' +
                        '<li><span>Rate the movie:</span></li>'+
                        '<fieldset class="rating '+stars_name+'">\n' +
                        '    <input type="radio" id="star10'+stars_name+'" name="'+stars_name+'" value="10" /><label class = "full" for="star10'+stars_name+'" title="Awesome - 10 stars"></label>\n' +
                        '    <input type="radio" id="star9'+stars_name+'" name="'+stars_name+'" value="9" /><label class="full" for="star9'+stars_name+'" title="Pretty good - 9 stars"></label>\n' +
                        '    <input type="radio" id="star8'+stars_name+'" name="'+stars_name+'" value="8" /><label class = "full" for="star8'+stars_name+'" title="Pretty good - 8 stars"></label>\n' +
                        '    <input type="radio" id="star7'+stars_name+'" name="'+stars_name+'" value="7" /><label class="full" for="star7'+stars_name+'" title="Good - 7 stars"></label>\n' +
                        '    <input type="radio" id="star6'+stars_name+'" name="'+stars_name+'" value="6" /><label class = "full" for="star6'+stars_name+'" title="Good - 6 stars"></label>\n' +
                        '    <input type="radio" id="star5'+stars_name+'" name="'+stars_name+'" value="5" /><label class="full" for="star5'+stars_name+'" title="5 stars"></label>\n' +
                        '    <input type="radio" id="star4'+stars_name+'" name="'+stars_name+'" value="4" /><label class = "full" for="star4'+stars_name+'" title="4 stars"></label>\n' +
                        '    <input type="radio" id="star3'+stars_name+'" name="'+stars_name+'" value="3" /><label class="full" for="star3'+stars_name+'" title="3 stars"></label>\n' +
                        '    <input type="radio" id="star2'+stars_name+'" name="'+stars_name+'" value="2" /><label class = "full" for="star2'+stars_name+'" title="2 stars"></label>\n' +
                        '    <input type="radio" id="star1'+stars_name+'" name="'+stars_name+'" value="1" /><label class="full" for="star1'+stars_name+'" title="1 star"></label>\n' +
                        '</fieldset>' +
                        '</ul>' +
                        '</div>' +
                        '<div class="col-xs-12 col-sm-12 col-md-7 excerpet">' +
                        '<h3>' + title + '</h3>' +
                        ' <p>' + overview + '</p>' +
                        '<div style="align: right" class="plus">' +
                        '<button class="btn btn-de" id="deleteFromWatchlist" title="Delete from watchlist">Delete from watchlist</button></div>' +
                        '</div>' +
                        '<span class="clearfix borda"></span>' +
                        '</article>' +
                        '</article>');
                }

                $("input[name^='rating']").change(function () {
                    var parentss = $(this).parents();
                    var divv = parentss[2];
                    var film_id = divv.children[0].value;
                    films[film_id].vote = $(this).val();
                    divv = parentss[1];
                    span = divv.children[3].children[0];
                    span.innerHTML = "Your rating: "+$(this).val();
                    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    var csrfToken = $("meta[name='_csrf']").attr("content");
                    var headers = {};
                    headers[csrfHeader] = csrfToken;
                    var obj = '{'
                        +'"id" : "'+films[film_id].id+'",'
                        +'"vote_average" : "'+films[film_id].vote_average+'",'
                        +'"vote" : "'+films[film_id].vote+'",'
                        +'"title" : "",'
                        +'"poster_path" : "",'
                        +'"original_language" : "",'
                        +'"overview" : "",'
                        +'"release_date" : ""'
                        +'}';
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/api/rateFilm",
                        data: obj,
                        dataType: 'json',
                        cache: false,
                        timeout: 6000,
                        headers: headers,
                        success: function (data) {
                            span.innerHTML = "Your rating: "+$(this).val();
                        },
                        error: function (e) {
                            alert("Server problems. Try again later.");
                        }
                    });
                });
                $(document).on('click', "#deleteFromWatchlist", function () {
                    curButton = $(this);
                    var parents = $(this).parents();
                    div = parents[2];
                    var num = div.children[1].children[0].value;

                    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    var csrfToken = $("meta[name='_csrf']").attr("content");
                    var headers = {};
                    headers[csrfHeader] = csrfToken;
                    var obj = '{'
                        +'"id" : "'+films[num].id+'",'
                        +'"vote_average" : "'+0+'",'
                        +'"vote" : "'+0+'",'
                        +'"title" : "",'
                        +'"poster_path" : "",'
                        +'"original_language" : "",'
                        +'"overview" : "",'
                        +'"release_date" : ""'
                        +'}';

                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/api/deleteFromWatchlist",
                        data: obj,
                        dataType: 'json',
                        cache: false,
                        timeout: 6000,
                        headers: headers,
                        success: function (data) {
                            if(data.status === "success") {
                                curButton.addClass("btn-success");
                                curButton.html("Successfully deleted");
                                curButton.parents()[3].remove();
                            } else {
                                curButton.addClass("btn-success");
                                curButton.html("Already deleted");
                            }
                        },
                        error: function (e) {
                            alert("Server problems. Try again later.");
                        }
                    });
                });
            } else {
                $("#results").append("Your watchlist is empty");
            }
        },
        error: function (e) {
            alert("Server problems. Try again later.");
        }
    });
}