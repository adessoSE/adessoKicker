var teamIds1 = [];
var teamNames1 = [];
var teamIds2 = [];
var teamNames2 = [];
var teamIds3 = [];
var teamNames3 = [];
var teamIds4 = [];
var teamNames4 = [];
var teamIds5 = [];
var teamNames5 = [];
var teamIds6 = [];
var teamNames6 = [];
var teamBoxes = 16;
var levels = 0;
var currentLevel;
var tournamentName = 'Adesso Einsteiger Tournier';
var tournamentStartDate = '19. September 2018';


function extractParams () {
	
	var i;
	
	if(tournamentTree.length > 2) {
		
		for(i = 0; i < tournamentTree.length; i++) {
			
			if(tournamentTree[i] == "]") {
				
					levels ++;
			}
		}
		levels --;
		
		alert("levels: " + levels);
		
		while(tournamentTree.slice(0, tournamentTree.indexOf(",")) != " null"){
			
			var level = tournamentTree.slice(tournamentTree.indexOf("Team{") + 5, index = tournamentTree.indexOf("},"));
			
			alert(level);
			
			level = level.slice(level.indexOf("=") + 1);
			teamIds1.push(level.slice(0, level.indexOf(",")));
			
			if(level.search("=&#39;") != -1) {
				level = level.slice(level.indexOf(";") + 1);
				teamNames1.push(level.slice(0, level.indexOf("&#39;")));
			}
			else {
				level = level.slice(level.indexOf("=") + 1);
				teamNames1.push(level.slice(0, level.indexOf(",")));
			}
			
			tournamentTree = tournamentTree.slice(tournamentTree.indexOf("}") + 2);
		}
	}
}


function setHeadline () {
	
	document.getElementById('heading').innerHTML = tournamentName + ' - ' + tournamentStartDate;
	
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

extractParams();
setHeadline();
setLevels();
printTree();
printLines();
