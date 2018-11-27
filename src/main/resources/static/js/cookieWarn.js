
//writes cookie to Browser storage
function setCookie(cname, cvalue, exdays) {

    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

//reads cookie attributes
function getCookie(cname) {

    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

//executes popWarning() if cookie is not set or expired
function checkCookie() {

    var hadWarn = getCookie("hasCook");

    if (hadWarn == "" || hadWarn == false) {

        popWarning();
        setCookie("hasCook", true, 14);
    }
}

//attaches a warning box to the top of the site
function popWarning (){

    var warnBox = document.createElement('div');
    warnBox.id='cook';
    var html = "<div style='position: fixed; z-index: 100; top: 0; color: white; background-color: black; height: 200px; padding: 50px; font-size: 30px; width: 100%;'>By continuing using this site you are agreeing with the usage of cookies<div style='color: white; float: right; width: 100px; text-align: center; padding: 10px; border-style: solid; border-color: gray;' onclick='removeWarning()' >OK</div></div>";
    warnBox.innerHTML = html;
    document.body.insertBefore(warnBox, document.body.firstChild);
}

//removes the warning box
function removeWarning () {

    document.body.removeChild(document.getElementById('cook'));
}

checkCookie();