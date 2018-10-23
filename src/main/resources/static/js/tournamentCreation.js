$(document).ready(function(){
    $('select[name="tournamentFormats"]').change(function(){
        $("#form").load('create?' + $(this).val());
    });
});