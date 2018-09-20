
function onMouseInteractDiv(div) {
	
	div.style.backgroundColor = 'lightgrey';
	
		div.style.boxShadow = '12px 12px 1px black';

}

function outMouseInteractDiv(div) {
	
	div.style.backgroundColor = 'white';
	div.style.boxShadow = '6px 6px black';
}

function toggleNotificationList() {
	
	if(document.getElementById('notificationList')){
		
		closeNotificationList();
	}
	else{
		
		openNotificationList();
		insertNotifications();
	}
}

function openNotificationList() {
	
	var posx,posy;
	
	posx = (document.getElementById('notificationIcon').style.left) - 25 + 'px';
	posy = document.getElementById('notificationIcon').style.top + 'px';
	var mainDiv = document.createElement('div');
	mainDiv.style = 'overflow-y: scroll; background-color: white; z-index: 10; Position: absolute; margin-bottom: 25px; border-style: groove; textAlign: left; height:400px; width: 225px; left:' + posx + '; top:' + posy +';';
	mainDiv.id = 'notificationList';
	document.getElementById('note').appendChild(mainDiv);
}

function closeNotificationList() {
	
	document.getElementById('note').removeChild(document.getElementById('notificationList'));
}

function getNotificationAlert() {
	
	var isNotification = true;
	 
	if(isNotification) {
		 
		var posx,posy;
	
		posx = (document.getElementById('notificationIcon').style.left) + 55  + 'px';
		posy = (document.getElementById('notificationIcon').style.top) - 2  + 'px';
		var noteDiv = document.createElement('div');
		noteDiv.style = 'color: red; font-weight: bold; background-color: none; zIndex: 10; width: 20px; height: 20px; position: absolute; text-align: center; left:' + posx + '; top:' + posy +';';
		noteDiv.id = 'isNote';
		noteDiv.innerHTML = "!";
		document.getElementById('notificationIcon').appendChild(noteDiv);
	}
}

function insertNotifications () {
	
	var dateTime = "02.09 15:46", heading = "[HEADLINE/NAME]", text = "textextextext", isOption = true, i, x = 5, posx, posy;
	
	for (i = 0; i < x; i++) {
		
		if(i > 0) {
			
			posx = (document.getElementById('notification' + (i - 1)).style.left);
			posy = (document.getElementById('notification' + (i - 1)).style.top);
		}
		
		else{
			
			posx = (document.getElementById('notificationIcon').style.left);
			posy = (document.getElementById('notificationIcon').style.top);
		}
		
		var noteMainDiv = document.createElement('div');
		noteMainDiv.style = 'zIndex: 12; width: 200px; height: 112px;  background-color: lightgrey; border-style: groove;';
		noteMainDiv.id = 'notification' + i;
		noteMainDiv.class = 'container';
		
		var noteHead = '<div style="zIndex: 15; position: absolute;"> ' + heading +'</div><div style="left: ' + (posx + 176) + 'px; width: 20px; height: 25px; border-style: groove; zIndex: 17; position: absolute; font-weight: bold; text-align: center;" onclick="deleteNotification(this.parentNode.id)"><span class="glyphicon glyphicon-remove"></div>';
		var noteOption = '<div style="left: ' + (posx + 115) + 'px; height: 25px; border-style: groove; zIndex: 17; position: absolute; font-weight: bold; text-align: center;" onclick="acceptNotification(this.parentNode.id)">Annehmen</div>'
		
		noteMainDiv.innerHTML = noteHead + '<br>' + text + '<br><br><br>';
		
		if(isOption) {
			noteMainDiv.innerHTML += noteOption + dateTime;
		}
		else {
			noteMainDiv.innerHTML += dateTime;
		}
		
		document.getElementById('notificationList').appendChild(noteMainDiv);
	}
}

function deleteNotification (notification) {
	
	document.getElementById('notificationList').removeChild(document.getElementById(notification));
}

function acceptNotification (notification) {
	
	document.getElementById('notificationList').removeChild(document.getElementById(notification));
}

getNotificationAlert();