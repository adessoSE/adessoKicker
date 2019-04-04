// Apply animation to each table row with delay
$(window).on('load', function () {
    var time = 0;
    var delay = 20;
    /* If we have more than x elements in the list, we just animate them without delay
     * As with delay the displaying of the table rows could take for every.
     */
    var i = 0;
    var stopDelayAfter = 20;
    $('tr').each(function () {
        var tr = this;
        setTimeout(function () {
            $(tr).css('animation-play-state', 'running')
        }, time);
        i++;
        if (i < stopDelayAfter) {
            time += delay;
        }
    });
});

function nextPage() {
    var size = findGetParameter("size");
    var page = Number(findGetParameter("page"));
    if (page == null) {
        page = 1;
    } else {
        page += 1;
    }
    if (size == null) {
        size = 10;
    }
    location.href = "/ranking?page=" + page + "&size=" + size;
}

function previousPage() {
    var size = findGetParameter("size");
    var page = Number(findGetParameter("page"));
    if (page == null || page === 0) {
        page = 0;
    } else {
        page -= 1;
    }
    if (size == null) {
        size = 10;
    }
    location.href = "/ranking?page=" + page + "&size=" + size;
}

function findGetParameter(parameterName) {
    var result = null,
        tmp = [];
    location.search
        .substr(1)
        .split("&")
        .forEach(function (item) {
            tmp = item.split("=");
            if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
        });
    return result;
}

$(document).ready(function () {
    // Enable tooltip
    $('[data-toggle="tooltip"]').tooltip();

    $('#search-bt').click(function(){

        if ($('#search-bar-selected').length > 0){
            window.location.href = $('#search-bar-selected').attr('href');
        }
    });
});
