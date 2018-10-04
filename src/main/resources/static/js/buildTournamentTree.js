var teams, teamBoxes = 16, levels, currentLevel, tournamentName = 'Adesso Einsteiger Tournier', tournamentStartDate = '19. September 2018';


function setHeadline () {
	
	document.getElementById('heading').innerHTML = tournamentName + ' - ' + tournamentStartDate;
	
}

function setTeamBoxes () {
	
	/*<![CDATA[*/
    var tmp = [[${teams}]];
	altert(tmp);
    teams = tmp.length;
    /*]]>*/
	
	
}

function fillTeamBoxes() {
	
}

function setLevels () {
	

	if(!currentLevel) {
		
		var i = teamBoxes;
		levels = 1;
		
		for(i; i != 1; i /= 2){
			
			if((i % 1) != 0) {
				
				i += (1 - (i % 1));
			}
				
			levels ++;
		}
	}
	else {
		
		levels = currentLevel;
	}
}

function printTree (){
	
	var i = 0, j = 0, teamBoxes = "", level = "", posx;
	
	var boxStyle = "border-radius: 5px; background-color: white; border-style: groove; padding: 5px; margin: 3px; box-shadow: 2px 2px 1px black;";
	var divEvents = "onmouseover='onMouseInteractDiv(this)' onmouseout='outMouseInteractDiv(this)'";
	
	for(i = 0; i < levels; i ++){
		
		
		level = '<div class="container" id="level' + i + '" style="display: flex; flex-flow: row nowrap; justify-content: space-between; z-index: 5;">'
		
		
		teamBoxes = "";
		
		for(j = 0; j < (teamBoxes / Math.pow(2, i)); j++) {
			
			if(i > 0) {
			
				posx = (((document.getElementById('level' + (i - 1) + 'team' + (j * 2)).getBoundingClientRect().left + document.getElementById('level' + (i - 1) + 'team' + ((j * 2) + 1)).getBoundingClientRect().right) / 2) - (document.getElementById('level' + (i - 1) + 'team' + (j * 2)).offsetWidth / 2));
				teamBoxes += '<div class="" id="level' + i + 'team' + j +'" ' + divEvents + ' style="left: ' + posx + 'px; ' + boxStyle + ' position: absolute;" ></div>';
			}
			else {
				teamBoxes += '<div class="" id="level' + i + 'team' + j +'" ' + divEvents + ' style="' + boxStyle + '" ></div>';
			}
		}
		
		document.getElementById('tree').innerHTML += level + teamBoxes + '</div><br><br><br><br><br>';
	}
}

function printLines () {
	
	var team1, team2, target, canvas, ctx, fixlx, fixly, fixrx, fixry, i, j;
	
	for(i = 1; i < levels; i ++){
		
		
		for(j = 0; j < (teamBoxes / Math.pow(2, i)); j++) {
	
			
			canvas = document.getElementById('canvas');
			ctx = canvas.getContext("2d");
			team1 = document.getElementById('level' + (i - 1) + 'team' + (j * 2));
			team2 = document.getElementById('level' + (i - 1) + 'team' + ((j * 2) + 1));
			target = document.getElementById('level' + i + 'team' + j);
			
			fixlx = returnCenter(team1);
			fixly = team1.getBoundingClientRect().bottom + 40;
			
			fixrx = returnCenter(team2);
			fixry = team2.getBoundingClientRect().bottom + 40;
			
			ctx.moveTo(fixlx, team1.getBoundingClientRect().bottom);
			ctx.lineTo(fixlx, fixly);
			ctx.moveTo(fixrx, team2.getBoundingClientRect().bottom);
			ctx.lineTo(fixrx, fixry);
			ctx.moveTo(fixlx, fixly);
			ctx.lineTo(fixrx, fixry);
			ctx.moveTo(returnCenter(target), fixly);
			ctx.lineTo(returnCenter(target), target.getBoundingClientRect().top);
			ctx.stroke();
		
		}
	}
}

function returnCenter (element) {
	
	return ((element.getBoundingClientRect().left + element.getBoundingClientRect().right) / 2);
}

setTeamBoxes();
setHeadline();
setLevels();
printTree();
printLines();