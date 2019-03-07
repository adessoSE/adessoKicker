$(document).ready(function () {

    var searchbarInput = $(".search-bar input");

    //Display 'search-bar-content' when clicking in a searchbar input
    searchbarInput.on("focus", function () {
        $(this).parents('.search-bar').find(".search-bar-content").toggle();
    });

    //Hide 'search-bar-content' when clicking anywhere else (loses focus)
    searchbarInput.on("blur", function () {
        var thisSearchbarInput = this;
        /* When you click on an item in the 'search-bar-content' the 'search-bar-input' loses focus.
         * Causing the 'search-bar-content' to vanish, so the action that should happen doesn't work.
         * As the 'blur-event' happens before the 'click-event'. A small delay is a workaround to prevent this.
         */
        setTimeout(function(){$(thisSearchbarInput).parents('.search-bar').find(".search-bar-content").toggle();}, 200);
    });

    //Filters the list elements of 'search-bar-content' and hides the elements that doesn't match the filter requirements
    searchbarInput.on("keyup", function (event) {
        var value = $(this).val().toLowerCase();
        $(this).parents('.search-bar').find(".list-group li").filter(function () {
            //indexOf() returns the positions of the char typed in the search bar (returns -1 if word doesnt contain char --> toggle element)
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
        //Trigger 'onclick' when enter pressed in input field
        if (event.keyCode === 13) {
            event.preventDefault();
            alert('Enter search');
        }
    });
});