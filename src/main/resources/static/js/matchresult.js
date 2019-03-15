$(document).ready(function () {
    // Enable tooltip
    $('[data-toggle="tooltip"]').tooltip();

    // Set default date
    document.getElementById('datetimepicker').valueAsDate = new Date();

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

    function toggleTrophies(selectedIcon, otherIcon, selectedRadio, otherRadio) {
        selectedIcon.removeClass('trophy-selected');
        selectedIcon.addClass('outline-trophy');
        otherIcon.removeClass('outline-trophy');
        otherIcon.addClass('trophy-selected');
        selectedRadio.prop("checked", false);
        otherRadio.prop("checked", true);
    }

    /* As Thymeleaf don't recognize that the radio button is selected by default
     * We trigger the radio button to update the field value by force.
     * The only other way would be to set the variable 'winnerTeamA' to try by default in the constructor,
     * But this doesn't seem ideal to me, as people don't expect a boolean to be true by default.
     */
    radio1.prop("checked", true);
});

function selectSearchedUser(element) {
    var userID = $(element).attr('value');
    var userName = $(element).find('span').html();
    var inputGroup = $(element).parents('.search-bar').children('.input-group');
    var hiddenInput = $(inputGroup).children('.hidden-input');
    var pseudoInput = $(inputGroup).children('.pseudo-input');
    var searchBarContent = $(element).parents('.search-bar').find('.search-bar-content');

    /* Notice: 'attr()' changes the value attribute (aka the default value) and 'val()'
     * Changes the actual value of the DOM element
     */
    $(hiddenInput).val(userID);
    $(pseudoInput).val(userName);

    $(searchBarContent).hide();
}