$(document).ready(function () {

    var searchbarInput = $(".search-bar input");

    $('.search-bar-content li').mouseover(function(){
        var searchbar = $(this).parents('.search-bar');
        var searchbarElements = $(searchbar).find(".list-group li");

        setNewSelected(searchbarElements, $(this));
    });

    // Display 'search-bar-content' when clicking in a searchbar input
    searchbarInput.on("focus", function () {
        var searchbar = $(this).parents('.search-bar');
        var seachbarElements = $(searchbar).find(".list-group li");

        searchbar.find(".search-bar-content").toggle();
        setNewSelected(seachbarElements, $(seachbarElements).siblings(":visible").first());
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
        }, 200);
    });

    searchbarInput.on("keyup", function (event) {
        var UP = 38;
        var DOWN = 40;
        var ENTER = 13;
        var value = $(this).val().toLowerCase();
        var searchbar = $(this).parents('.search-bar');
        var searchbarElements = $(searchbar).find(".list-group li");
        var searchbarVisibleElements = $(searchbarElements).siblings(":visible");

        if (event.keyCode === ENTER) {
            $('#search-bar-content-selected').click();
            return;
        } else if (event.keyCode === UP) {
            console.log($('#search-bar-content-selected').prevAll('li:visible').html());
            setNewSelected(searchbarElements, $('#search-bar-content-selected').prevAll('li:visible').eq(0));
            return;
        } else if (event.keyCode === DOWN) {
            console.log($('#search-bar-content-selected').nextAll('li:visible').html());
            setNewSelected(searchbarElements, $('#search-bar-content-selected').nextAll('li:visible').eq(0));
            return;
        }

        // Filters the list elements of 'search-bar-content' and hides the elements that doesn't match the filter requirements
        searchbarElements.filter(function () {
            //indexOf() returns the positions of the char typed in the search bar (returns -1 if word doesnt contain char --> toggle element)
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)

        });

        // Set first visible li as selected
        setNewSelected(searchbarElements, $(searchbarElements).siblings(":visible").first());
    });

    function setNewSelected(searchbarElements, newSelected) {
        if(newSelected.length > 0){
            // Remove id from all
            $(searchbarElements).each(function (element) {
                $(this).removeAttr('id');
            });
            $(newSelected).attr("id", "search-bar-content-selected");
        }
    }
});