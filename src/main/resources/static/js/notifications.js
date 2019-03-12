function updateBadgeCount() {
    var notificationBadge = document.getElementById('notification-badge');
    var temp = parseInt(notificationBadge.getAttribute('value')) - 1;
    notificationBadge.setAttribute('value', temp);
    notificationBadge.innerText = notificationBadge.getAttribute('value');
    //Hide badge if no notifications
    if (temp <= 0) {
        notificationBadge.hidden = true;
    }
}

function removeNotification(btn) {
    $(btn).parents('.notification').remove();
    var list = $('#notification-dropdown-menu')
    var noNotifications = $('#no-notifications');
    if (list.children().length == 1) {
        noNotifications.toggle();
    }
}

function accept(btn) {
    var xhttp = new XMLHttpRequest();
    var id = btn.parentNode.parentNode.parentNode.getAttribute('value');
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            removeNotification(btn);
            updateBadgeCount();
        }
    };
    xhttp.open("GET", "/notifications/accept/" + id, false);
    xhttp.send();
    return false;
}

function decline(btn) {
    var xhttp = new XMLHttpRequest();
    var id = btn.parentNode.parentNode.parentNode.getAttribute('value');
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            removeNotification(btn);
            updateBadgeCount();
        }
    };
    xhttp.open("GET", "/notifications/decline/" + id, false);
    xhttp.send();
    return false;
}