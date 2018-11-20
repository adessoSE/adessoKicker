//--> Sends a XML-HTTP Request that deletes notification
function deleteNotification(notificationId){
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		
		//Remove notification on page
		$('#notificationList').children().each(function(){
			var value = $(this).attr("value");
			if (value == notificationId){
				$(this).remove();
			}
		});
	}
	};
	xhttp.open("DELETE", "/notifications/"+notificationId, false);
	xhttp.send();
	return false;
}

//Sends a DELETE Request to "notification/accept
function acceptNotification(notificationId) {
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		
		//Remove notification on page
		$('#notificationList').children().each(function(){
			var value = $(this).attr("value");
			if (value == notificationId){
				$(this).remove();
			}
		});
	}
	};
	xhttp.open("DELETE", "/notifications/accept/"+notificationId, false);
	xhttp.send();
	return false;
}

//--> Triggered on Notification Button click
function toggleNotificationList() {
	
	$('#notificationList').toggle();
}