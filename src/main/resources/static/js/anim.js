function toggleEnvelope(){
    $('#notification-dropdown-icon').find('i').toggleClass('fa-envelope fa-envelope-open');
}

$(document).ready(function() {
    // Visualizes the selected radio button
    $("#form-check-1").click(function() {
        $('#radio2-icon').removeClass('trophy-selected');
        $('#radio1-icon').addClass('trophy-selected');
    });
    $("#form-check-2").click(function() {
        $('#radio1-icon').removeClass('trophy-selected');
        $('#radio2-icon').addClass('trophy-selected');
    });
});