$(document).ready(function () {
    // Toggle tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Shows/Disables trophy on select
    $("#form-check-1").click(function () {
        $('#radio2-icon').removeClass('trophy-selected');
        $('#radio1-icon').addClass('trophy-selected');
    });
    $("#form-check-2").click(function () {
        $('#radio1-icon').removeClass('trophy-selected');
        $('#radio2-icon').addClass('trophy-selected');
    });
});