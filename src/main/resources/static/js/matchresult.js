$(document).ready(function () {
    // Toggle tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Shows/Disables trophies on click
    var icon1 = $('#radio1-icon');
    var icon2 = $('#radio2-icon');
    var radio1 = $("#radio1");
    var radio2 = $("#radio2");
    $('#form-check-1').click(function () {
        toggleTrophies(icon2, icon1, radio2, radio1);
    });
    $('#form-check-2').click(function () {
        toggleTrophies(icon1, icon2, radio1, radio2);
    });
    function toggleTrophies(selectedIcon, otherIcon, selectedRadio, otherRadio){
        selectedIcon.removeClass('trophy-selected');
        selectedIcon.addClass('outline-trophy');
        otherIcon.removeClass('outline-trophy');
        otherIcon.addClass('trophy-selected');
        selectedRadio.prop("checked", false);
        otherRadio.prop("checked", true);
    }
});