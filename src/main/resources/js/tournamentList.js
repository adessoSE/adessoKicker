//On document load
$(function () {
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
    var id = 0;
    var name = "Adesso Einsteiger Turnier";
    var date = "19.09.2018";
    var time = "14.00 Uhr - 17 Uhr";
    var place = "Dortmund Kicker 1";
    var describtion = "Raucherraum in der dortmunder Geschäftstelle, Currywurst und Getränke vor Ort. Alle Kollegen/innen sind eingeladen zu kommen wir freuen uns auf alle Leute die Zeit haben.";

    //Expired
    var isExpired = false;
    var scrollEl;
    if (isExpired == true) {
        scrollEl = $("<div class='scrollElement_expired'></div>");
    }
    else {
        scrollEl = $("<div class='scrollElement'></div>");
    }

    //Elements
    var well = $("<div class='well'></div>");
    var h3 = $("<h3>" + name + "</h3>");
    var header = $("<p style='font-size: 20px;'>" + date + " | " + time + " | " + place + "</p>");
    var hr = $("<hr>");
    var containerMatch = $("<div class='containerMatch'></div>");
    var el_A = $("<div class='elementMatch'></div>");
    var el_B = $("<div class='elementMatch2'></div>");
    var text = $("<p>" + describtion + "</p>");
    //Function binding
    var bt = $("<button onclick='viewTournament("+id+");' class='.btn-primary'>Ansehen</button>");  
    //Child-Parent
    el_A.append(header, text);
    el_B.append(bt);
    scrollEl.append(well);
    well.append(h3, hr, containerMatch);
    containerMatch.append(el_A, el_B);
    //Add new element to scrollbar
    $("#scrollbar").append(scrollEl);
}

//Button Event --> View a specific tournament(URL access)
function viewTournament(id) {
    //alert("View Tournament " + id);
    //window.open("/tournaments/" + id);
    window.open("tournamentList.html");
}