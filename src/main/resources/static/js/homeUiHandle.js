
//highlights box
function onMouseInteractDiv(div) {
	
	div.style.backgroundColor = 'lightgrey';
	
		div.style.boxShadow = '12px 12px 1px black';

}

//box back to normal
function outMouseInteractDiv(div) {
	
	div.style.backgroundColor = 'white';
	div.style.boxShadow = '6px 6px black';
}

//opens notificationlist and inserts notifications if not open. Else close the list
function toggleNotificationList() {
	
	if(document.getElementById('notificationList')){
		
		closeNotificationList();
	}
	else{
		
		openNotificationList();
		insertNotifications();
	}
}

//creates and attaches notificationlist box
function openNotificationList() {
	
	var posx,posy;
	
	posx = (document.getElementById('notificationIcon').style.left) - 25 + 'px';
	posy = document.getElementById('notificationIcon').style.top + 'px';
	var mainDiv = document.createElement('div');
	mainDiv.style = 'overflow-y: scroll; background-color: white; z-index: 10; Position: absolute; margin-bottom: 25px; border-style: groove; textAlign: left; height:400px; width: 225px; left:' + posx + '; top:' + posy +';';
	mainDiv.id = 'notificationList';
	document.getElementById('note').appendChild(mainDiv);
}

//closes notificationlist box
function closeNotificationList() {
	
	document.getElementById('note').removeChild(document.getElementById('notificationList'));
}

//shows an big red exclamation mark if notifications are available
function getNotificationAlert() {
	
	var isNotification = true;   ///////////////////////////////////////////////// change declaration
	 
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

//inserts notifications into notificationlist box
function insertNotifications () {
	
	var dateTime = "02.09 15:46";
	var heading = "[HEADLINE/NAME]"; 
	var text = "textextextext"; 
	var isOption = true;
	var i; 
	var numNotes = 5; 
	var posx; 
	var posy;
	
	//ilterates through each notification
	for (i = 0; i < numNotes; i++) {
		
		//get position of notification above
		if(i > 0) {
			
			posx = (document.getElementById('notification' + (i - 1)).style.left);
			posy = (document.getElementById('notification' + (i - 1)).style.top);
		}
		//get position for first notification
		else{
			
			posx = (document.getElementById('notificationIcon').style.left);
			posy = (document.getElementById('notificationIcon').style.top);
		}
		
		//creates main container
		var noteMainDiv = document.createElement('div');
		noteMainDiv.style = 'zIndex: 12; width: 200px; height: 112px;  background-color: lightgrey; border-style: groove;';
		noteMainDiv.id = 'notification' + i;
		noteMainDiv.class = 'container';
		
		//creates content containers
		var noteHead = '<div style="zIndex: 15; position: absolute;"> ' + heading +'</div><div style="left: ' + (posx + 176) + 'px; width: 20px; height: 25px; border-style: groove; zIndex: 17; position: absolute; font-weight: bold; text-align: center;" onclick="deleteNotification(this.parentNode.id)"><span class="glyphicon glyphicon-remove"></div>';
		var noteOption = '<div style="left: ' + (posx + 115) + 'px; height: 25px; border-style: groove; zIndex: 17; position: absolute; font-weight: bold; text-align: center;" onclick="acceptNotification(this.parentNode.id)">Annehmen</div>'
		
		//inserts text contents into main container
		noteMainDiv.innerHTML = noteHead + '<br>' + text + '<br><br><br>';
		
		//adds control 'Annehmen' and time signature to main container if option is available
		if(isOption) {
			noteMainDiv.innerHTML += noteOption + dateTime;
		}
		//only adds time signature
		else {
			noteMainDiv.innerHTML += dateTime;
		}
		
		//inserts notification main container into notificationslist box
		document.getElementById('notificationList').appendChild(noteMainDiv);
	}
}

//deletes a notification and removes it from list
function deleteNotification (notification) {
	
	document.getElementById('notificationList').removeChild(document.getElementById(notification));
}

//accepts a notification and removes it from list
function acceptNotification (notification) {
	
	document.getElementById('notificationList').removeChild(document.getElementById(notification));
}

getNotificationAlert();