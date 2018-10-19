
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