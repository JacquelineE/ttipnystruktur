//@author aguler


//create xmlhttpreq object
function makeCorsReq(u, func, c) {
  $.ajax({
    type: 'GET',
    dataType: 'json',
    url: u,
    async: true,
    success: function(resp){
      console.log("Established contact with server at: " + u);
      if (isJSON(JSON.stringify(resp))) { 
        var json = JSON.parse(JSON.stringify(resp));
        if (json.errorId) {
          console.error("Error id: "+ json.errorId + " httpStatus: " + json.httpStatus)
          console.error(json.message);
        }
        else {
          console.log("Request went through.");
          func(resp, c);
        }
      } else {
        console.error("Not JSON-data.");
      }
    },
    error: function(xhr){
      console.error("Error status: " + xhr.status);
    }
  });
}

function isJSON(str) {
	try {
		JSON.parse(str);
		return true;
	} catch (e) {
		return false;
	}
}


