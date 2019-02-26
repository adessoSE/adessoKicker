function toggleEnvelope(){
    $('#notification-dropdown-icon').find('i').toggleClass('fa-envelope fa-envelope-open');
}

$(document).ready(function() {
    // Visualizes the selected radio button
    $("#radio-winner-team input:radio").click(function() {
        if($(this).attr('id') === 'radio1'){
            $('#radio2-icon').removeClass('trophy-selected');
            $('#radio1-icon').addClass('trophy-selected');
        }
        else if($(this).attr('id') === 'radio2'){
            $('#radio1-icon').removeClass('trophy-selected');
            $('#radio2-icon').addClass('trophy-selected');
        }
    });
});