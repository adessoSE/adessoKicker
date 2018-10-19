function sendAjaxRequest() {
    var country = $("#tournamentFormats").val();
    $.get( "/tournaments/create?" + tournamentFormat, function( data ) {
        $("#region").empty();
        data.forEach(function(item, i) {
            var option = "<option value = " + item + ">" + item +  "</option>";
            $("#region").append(option);
        });
    });
};