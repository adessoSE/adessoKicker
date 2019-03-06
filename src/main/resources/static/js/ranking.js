
function nextPage() {
    var size = findGetParameter("size");
    var page = Number(findGetParameter("page"));
    if(page == null) {
        page = 1;
    } else {
        page += 1;
    }
    if(size == null){
        size = 10;
    }
    location.href = "/ranking?page=" + page + "&size=" + size;
}

function previousPage() {
    var size = findGetParameter("size");
    var page = Number(findGetParameter("page"));
    if(page == null || page == 0) {
        page = 0;
    } else {
        page -= 1;
    }
    if(size == null){
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