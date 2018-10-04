/*$(function () {
    //[User/Matches/Count]
    var matchCount = 2000;

    //Create match elements
    for (var i = 0; i < matchCount; i++) {
        CreateMatchEl();
    }
});

//Create a single Match element
function CreateMatchEl() {

    //Match information
    var date = "19.09.2018";
    var time = "14.00 Uhr";
    var place = "Kicker 1";
    var teamA_1 = "Jan Schneider";
    var teamA_2 = "Florian Albers";
    var teamB_1 = "Jan Schneider";
    var teamB_2 = "Florian Albers";

    //Match result
    var result = "";
    if (1 == 1) {
        result = "<span class='glyphicon glyphicon-ok'></span>";
    }
    else if(0==2){
        result = "<span class='glyphicon glyphicon-remove'></span>";
    }

    //Expired
    var isExpired = false;
    var scrollEl;
    if (isExpired == true) {
        scrollEl = $("<div class='scrollElement_expired'></div>");
    }
    else {
        scrollEl = $("<div class='scrollElement'></div>");
    }

    var well = $("<div class='well'></div>");
    var header = $("<p>" + date + " | " + time + " | " + place + "</p > ");
    var hr = $("<hr>");
    var containerMatch = $("<div class='containerMatch'></div>");

    var el_A1 = $("<div class='elementMatch'></div>");
    var el_A2 = $("<div class='elementMatch'></div>");
    var el_B1 = $("<div class='elementMatch2'></div>");
    var el_B2 = $("<div class='elementMatch2'></div>");

    var box = $("<div class='box'>" + result + "</div>");
    var team1 = $("<div class='team'><p>" + teamA_1 + "</p > <p>" + teamA_2 + "</p></span ></div>");
    var vs = $("<p style='font-size: 80px; text-align: center;'>VS.</p>");
    var team2 = $("<div class='team'><p>" + teamB_1 + "</p><p>" + teamB_2 + "</p></span></div>");

    el_A1.append(box); el_A2.append(vs); el_B1.append(team1); el_B2.append(team2);

    scrollEl.append(well);
    well.append(header, hr, containerMatch);
    containerMatch.append(el_A1, el_B1, el_A2, el_B2);

    $("#scrollbar").append(scrollEl);
}
*/