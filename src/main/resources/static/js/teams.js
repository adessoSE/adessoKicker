
function mouseOver(div) {
	
	div.style.backgroundColor = "lightgrey";
}

function mouseOut(div) {
	
	div.style.backgroundColor = "white";
}

function sort (sortMode = "") {
	
	var list = document.getElementById('list');
	var elements = list.children.length;
	var firstChild;
	var	teamNames = [];
	var	playerANames = [];
	var	playerBNames = [];
	var	teamWins = [];
	var	teamLosses = [];
	var newTeamIndex = [];
	var newHtml = "";
	var i;
	var j;
	
	
	for(i = 0; i < elements; i++) {
		
		teamNames[i] = list.children[i].children[0].innerHTML;
		playerANames[i] = list.children[i].children[1].innerHTML;
		playerBNames[i] = list.children[i].children[2].innerHTML;  
		teamWins[i] = parseInt(list.children[i].children[3].innerHTML);
		teamLosses[i] = parseInt(list.children[i].children[4].innerHTML);
	}
	
	
	if(sortMode == "wins") {
				
		if(list.dataset.sorted != "winsDESC") {
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if(teamWins[j] >= teamWins[newTeamIndex[0]]){
						
						newTeamIndex.unshift(j);
						break;
					}
					else if(teamWins[j] <= teamWins[newTeamIndex[i]] && teamWins[j] >= teamWins[newTeamIndex[i + 1]]) {
						
						newTeamIndex.splice(i + 1, 0, j);
						break;
					}
					else if (i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = 'winsDESC';
		}
		else if (list.dataset.sorted == "winsDESC"){
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if(teamWins[j] <= teamWins[newTeamIndex[0]]){
						
						newTeamIndex.unshift(j);
						break;
					}
					else if(teamWins[j] >= teamWins[newTeamIndex[(j - 1)]] && teamWins[j] <= teamWins[newTeamIndex[i + 1]]) {
						
						newTeamIndex.splice(j + 1, 0, j);
						break;
					}
					else if (i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = 'winsASC';
		}
	}
	
	
	if(sortMode == "losses") {
		
		if(list.dataset.sorted != "lossesDESC") {
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if(teamLosses[j] >= teamLosses[newTeamIndex[0]]){
						
						newTeamIndex.unshift(j);
						break;
					}
					else if(teamLosses[j] <= teamLosses[newTeamIndex[i]] && teamLosses[j] >= teamLosses[newTeamIndex[i + 1]]) {
						
						newTeamIndex.splice(i + 1, 0, j);
						break;
					}
					else if (i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = 'lossesDESC';
		}
		else if (list.dataset.sorted == "lossesDESC"){
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if(teamLosses[j] <= teamLosses[newTeamIndex[0]]){
						
						newTeamIndex.unshift(j);
						break;
					}
					else if(teamLosses[j] >= teamLosses[newTeamIndex[(j - 1)]] && teamLosses[j] <= teamLosses[newTeamIndex[i + 1]]) {
						
						newTeamIndex.splice(j + 1, 0, j);
						break;
					}
					else if (i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = 'lossesASC';
		}
	}
	
	if(sortMode == "teamnames") {
		
		if(list.dataset.sorted != "teamnamesDESC") {
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if (teamNames[j].toLowerCase() <= teamNames[newTeamIndex[i]].toLowerCase() ) {
						
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = "teamnamesDESC";
		}
		else if(list.dataset.sorted == "teamnamesDESC") {
			
			newTeamIndex.push(0);
				
			for(j = 1; j < elements; j++) {
					
				var x = newTeamIndex.length;
					
				for(i = 0; i < x; i ++) {
						
					if (teamNames[j].toLowerCase() >= teamNames[newTeamIndex[i]].toLowerCase() ) {
							
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
							
						newTeamIndex.push(j);
						break;
					}
				}
			}
				
			list.dataset.sorted = "teamnamesASC";
		}
	}
	
	if(sortMode == "playerA") {
		
		if(list.dataset.sorted != "playerA_DESC") {
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if (playerANames[j].toLowerCase() <= playerANames[newTeamIndex[i]].toLowerCase() ) {
						
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = "playerA_DESC";
		}
		else if(list.dataset.sorted == "playerA_DESC") {
			
			newTeamIndex.push(0);
				
			for(j = 1; j < elements; j++) {
					
				var x = newTeamIndex.length;
					
				for(i = 0; i < x; i ++) {
						
					if (playerANames[j].toLowerCase() >= playerANames[newTeamIndex[i]].toLowerCase() ) {
							
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
							
						newTeamIndex.push(j);
						break;
					}
				}
			}
				
			list.dataset.sorted = "playerA_ASC";
		}
	}
	
	if(sortMode == "playerB") {
		
		if(list.dataset.sorted != "playerB_DESC") {
			
			newTeamIndex.push(0);
			
			for(j = 1; j < elements; j++) {
				
				var x = newTeamIndex.length;
				
				for(i = 0; i < x; i ++) {
					
					if (playerBNames[j].toLowerCase() <= playerBNames[newTeamIndex[i]].toLowerCase() ) {
						
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
						
						newTeamIndex.push(j);
						break;
					}
				}
			}
			
			list.dataset.sorted = "playerB_DESC";
		}
		else if(list.dataset.sorted == "playerB_DESC") {
			
			newTeamIndex.push(0);
				
			for(j = 1; j < elements; j++) {
					
				var x = newTeamIndex.length;
					
				for(i = 0; i < x; i ++) {
						
					if (playerBNames[j].toLowerCase() >= playerBNames[newTeamIndex[i]].toLowerCase() ) {
							
						newTeamIndex.splice(i, 0, j);
						break;
					}
					else if ( i == (x - 1)) {
							
						newTeamIndex.push(j);
						break;
					}
				}
			}
				
			list.dataset.sorted = "playerB_ASC";
		}
	}
	
	
	
	for(i = 0; i < elements; i++) {
		
		newHtml += "<div class='row teamBox'><div class='col-lg-2 value'>" + teamNames[newTeamIndex[i]] + "</div><div class='col-lg-3 value'>" + playerANames[newTeamIndex[i]] + "</div><div class='col-lg-3 value'>" + playerBNames[newTeamIndex[i]] + "</div><div class='col-lg-2 value'>" + teamWins[newTeamIndex[i]] + "</div><div class='col-lg-2 value'>" + teamLosses[newTeamIndex[i]] + "</div></div>";
	}
	
	list.innerHTML = newHtml;
}