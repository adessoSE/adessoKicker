$(document).ready(function () {
    // Keycodes
    var UP = 38;
    var DOWN = 40;
    var ENTER = 13;

    var searchbarInput = $(".search-bar input");

    // Select the hovered element
    $('.search-bar-content li').mouseover(function(){
        selectNewElement($(this));
    });

    // Hide 'search-bar-content' when clicking anywhere else (loses focus)
    searchbarInput.on("blur", function () {
        var thisSearchbarInput = this;
        /* When you click on an item in the 'search-bar-content' the 'search-bar-input' loses focus.
         * Causing the 'search-bar-content' to vanish, so the action that should happen doesn't work.
         * As the 'blur-event' happens before the 'click-event'. A small delay is a workaround to prevent this.
         */
        setTimeout(function () {
            $(thisSearchbarInput).parents('.search-bar').find(".search-bar-content").toggle();
        }, 100);
    });

    // Key controls
    searchbarInput.on("keydown", function (event) {
        var searchbar = $(this).parents('.search-bar');
        var searchbarElements = $(searchbar).find(".list-group li");
        var searchbarList = $(searchbar).find(".search-bar-content ul");
        var searchbarSelected = $('#search-bar-selected');

        if (event.keyCode === ENTER) {
            event.preventDefault();
            searchbarSelected.click();

        } else if (event.keyCode === UP) {
            event.preventDefault();
            var previousElement = $(searchbarSelected).prevAll('li:visible').eq(0);
            selectNewElement(previousElement);
            $(searchbarList).scrollTop($(previousElement).scrollHeight);
            var newScrollbarPos = $(searchbarList).scrollTop() - $(previousElement).prop('scrollHeight');
            if (!isNaN(newScrollbarPos)){
                $(searchbarList).scrollTop(newScrollbarPos);
            }

        } else if (event.keyCode === DOWN) {
            event.preventDefault();
            var nextElement = $(searchbarSelected).nextAll('li:visible').eq(0);
            selectNewElement(nextElement);
            var newScrollbarPos = $(searchbarList).scrollTop() + $(nextElement).prop('scrollHeight');
            if (!isNaN(newScrollbarPos)){
                $(searchbarList).scrollTop(newScrollbarPos);
            }
        }
    });

    // Search filter
    searchbarInput.on("keyup", function (event) {
        if(event.keyCode === ENTER || event.keyCode === UP || event.keyCode === DOWN){
            return;
        }
        var value = $(this).val().toLowerCase();
        var searchbar = $(this).parents('.search-bar');
        var searchbarElements = $(searchbar).find(".list-group li");
        var searchbarContent = $(searchbar).find(".search-bar-content");

        searchbarContent.show();

        searchbarElements.filter(function () {
            //indexOf() returns the positions of the char typed in the search bar (returns -1 if word doesn't contain char --> toggles element)
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
        selectNewElement($(searchbarElements).siblings(":visible").first());
    });

    function selectNewElement(element) {
        // To check if a JQuery object/result is valid we need to check for length > 0 (0 -> invalid)
        if(element.length > 0){
            $('#search-bar-selected').removeAttr('id');
            $(element).attr("id", "search-bar-selected");
        }
    }
});