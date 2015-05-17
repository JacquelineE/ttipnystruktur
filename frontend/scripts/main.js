function isIndex(){
    var pagePath = window.location.pathname;
    if (pagePath.substring(pagePath.lastIndexOf("/") + 1) == "index.html") {
    	return true;
    }
    return false;
}

function getChartLen() {
  var len = 0;
  while ($(".chartcont" + len).length) {
    len = len + 1;
  }
  return len;
}

function main() {
  if (isIndex()) {
    /*For interesting, disabled for now
    makeCorsReq(getURLInteresting(),createHome);
    */
    makeCorsReq(getURLInteresting("3"), createHome);
  }else {
    //TODO call makecors with URL for category and function for setting categories in automcomplete.js
    makeCorsReq(getURLCategories(),setSubjects);
    makeCorsReq(getURLInteresting("1"),createSearch);
  }
}

function createHome(resp) {
  var jsob = JSON.parse(JSON.stringify(resp));
  var jsobarr = [];
  for (var i = 0; i < jsob.topTrends.length; i++) {
    jsobarr[i] = jsob.topTrends[i];
  }
  //console.log(jsobarr.length);
  graphHomeInit(jsobarr);

/*  var jsob = JSON.parse(JSON.stringify(resp));
  graphHomeInit(jsob);
  */
  buttonsInit(getInteresting(jsobarr));
  navInit();
  infoTextInit();
  
}

function createSearch(resp) {
  var jsob = JSON.parse(JSON.stringify(resp));
  graphSearchInit(jsob);
  searchInit();
  buttonsInit(getInteresting(jsob.topTrends));
  infoTextInit();
}

function getInteresting(jsobarr) {
  var arr = [];
  var arrs = [];
  for (var i = 0; i < jsobarr.length; i++) {
    for (var j = 0; j < PARTIES.length; j++) {
      if (jsobarr[i].datasets[j].isIntersting === true) {
        arr.push(jsobarr[i].datasets[j].party);
      }
    }
    arrs.push(sortParties(arr));
    arr = [];
  }
  return arrs;
}

function sortParties(a) {
  var par = PARTIES;
  var arr = []; 
  for (var i = 0; i < PARTIES.length; i++) {
    for (var j = 0; j < a.length; j++) {
      if (a[j] === PARTIES[i]) {
        arr.push(a[j]);
      }
    }
  }
  return arr;
}

$(document).ready(main);