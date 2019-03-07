$(document).ready(function () {
    //When clicking on a seachbar
    $(".searchbar input").on("focus", function () {
        $(".searched-user").toggle();
    });
    //Leaving' the search bar
    $(".searchbar input").on("blur", function () {
        //Short delay -> workaround to allow clicking on searched users without toggling them so you cant click them
        setTimeout(function(){$(".searched-user").toggle();}, 200);
    });
    //When typing a char in the seachbar
    $(".searchbar input").on("keyup", function (event) {
        var value = $(this).val().toLowerCase();
        $(".list-group li").filter(function () {
            //indexOf() returns the positions of the char typed in the search bar (returns -1 if word doesnt contain char --> toggle element)
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});