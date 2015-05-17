//lägg in info i html o gör toggla istället
var INFO = "Klicka på en datapunk i grafen för att få mer information om tidpunkten och länkar till motioner."
var ICONS_URL = "url(../img/buttons_clicked35.png)";
var PLUS_URL = "url(../img/plus_icon.png)"
var MINUS_URL = "url(../img/minus_icon.png)"

var charttexts= [];

function InfoText(infoname) {
	this.divName = "." + infoname;
	this.info = $("."+infoname);
	this.parties = [];
}

InfoText.prototype = {
	init: function() {
		$(this.divName + " #help").text(INFO);
		//create all css properties
		var y = 0;	
		for (var i = 0; i < PARTIES.length; i++) {
			this.getElement(PARTIES[i], "party").css({"background": ICONS_URL + "0px " + y + "px", "display": "none"});
			this.getElement(PARTIES[i], "max-min").css({"background": "url(../img/plus_icon.png)", "display": "none"});
			y = y - 35;
		}
	},
	getElement: function() {
		var elem = this.info;
		for (var i =0; i < arguments.length; i++) {
			if(document.getElementById(arguments[i])) {
				arguments[i] = "#"+arguments[i];
			}else {
				if (document.getElementsByClassName(arguments[i]).length > 0) {
					arguments[i] = "."+arguments[i];
				} else {
					console.error(arguments[i] + " is not an element");
				}
			}
			elem = elem.find(arguments[i]);
		}
 		return elem;
	},
	getLinks: function(party) {
		for (var i = 0; i < this.parties.length; i++) {
			if (this.parties[i].party === party) {
				return this.parties[i].links;
			}
		}
		console.error("Party " + p + " does not exist.");
		return null;
	},
	add: function (time, party, linkarr) {
		this.parties.push({party: party, links: linkarr});
		this.getElement("time-period").text("Tidpunkt: " + time);
		this.getElement(party).css({"display":""});
		var mot = " motion";
		if (linkarr.length > 1) mot += "er";
		this.getElement(party, "amount").append(linkarr.length + mot);
		this.getElement(party, "party").css({"display":""});
		this.getElement(party,"max-min").css({"display":""});

	},
	clearAll: function() {
		for (var i = 0; i < this.parties.length; i++) {
			this.getElement(this.parties[i].party, "amount").text("");
			this.getElement(this.parties[i].party).css({"display":"none"});
		}
		this.parties=[];
	}
	
}

function infoTextInit() {
	for (var t = 0; t < getChartLen(); t++) {
		charttexts.push(new InfoText("charttext"+t));
		charttexts[t].init();
	}
	for (var i = 0; i < charttexts.length; i++) {
		for (var j= 0; j < PARTIES.length; j++) {
			mouseEventHandler(i, PARTIES[j]);
		}
	}
}

function mouseEventHandler(i, party) {
	var clicked = false;
	var elem = charttexts[i].getElement(party, "max-min");
	elem.on("click", function() {
		elem.toggle();
		if (clicked) {
			elem.css({"background": PLUS_URL});
			charttexts[i].getElement(party+"motions").remove();
			//kill all children, use remove method maybe

		} else {
			elem.css({"background": MINUS_URL});
			//add tr children with motions
			var links = charttexts[i].getLinks(party);
			for (var j = 0; j < links.length; j++) {
				//gör så att den struntar i allt innan stor bokstav (tar bort med anledning av....)
            	charttexts[i].getElement(party).after("<tr class="+party+"motions><td><a href="+links[j].link+" target=_blank>"+links[j].date+"</a></td></tr>");
			}																//target="_blank"

            //<tr class="motions"><td><a href="http://www.twitter.com">Hello</a></td></tr>

		}
		elem.toggle();
		clicked ^=true;
	});
	elem.on("mouseenter", function() {
		elem.css({"opacity": "1"});
	});
	elem.on("mouseleave", function() {
		if (clicked!=true) elem.css({"opacity": "0.5"});

	});
	
}

function shorten(s) {
	for (var i = 0; i < s.length; i++) {
		var char = s.charAt(i);
		if (char != ' ' && char == char.toUpperCase()) {
			return s.slice(i);
		}
	}
	return s;
}

function clearAll(i) {
	charttexts[i].clearAll();
}

function addToInfo(time, index, party, linkarr) {
	//console.log("Under tidsperiod: " + time + " För parti " + party + " är den andra titeln: " + linkarr[0].title);
	charttexts[index].add(time, party, linkarr);
}

