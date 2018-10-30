$(document).ready(function(){
    $('select[name="tournamentFormats"]').change(function(){
        $("#tournamentForm").load('create?' + $(this).val());
    });
});