function updateBadgeCount(){
    var notificationBadge = document.getElementById('notification-badge');
    var temp = parseInt(notificationBadge.getAttribute('value')) - 1;
    notificationBadge.setAttribute('value', temp);
    notificationBadge.innerText = notificationBadge.getAttribute('value');
    //Hide badge if no notifications
    if (temp <= 0){
        notificationBadge.hidden = true;
    }
}

function accept(btn) {
    var notification = btn.parentNode.parentNode.parentNode;
    var id = notification.getAttribute('value');
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            notification.remove();
            updateBadgeCount();
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
            alert();
            notification.remove();
            updateBadgeCount();
        }
    }
    xhttp.open("GET", "/notifications/decline/" + id, false);
    xhttp.send();
    return false;
}