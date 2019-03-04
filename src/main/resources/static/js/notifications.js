
function accept(btn) {
    var notification = btn.parentNode.parentNode.parentNode;
    var id = notification.getAttribute('value');
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            notification.remove();
        }
    }
    xhttp.open("GET", "/notifications/accept/" + id, false);
    xhttp.send();
    return false;
}

function decline(btn) {
    var notification = btn.parentNode.parentNode.parentNode;
    var id = notification.getAttribute('value');
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            notification.remove();
        }
    }
    xhttp.open("GET", "/notifications/decline/" + id, false);
    xhttp.send();
    return false;
}