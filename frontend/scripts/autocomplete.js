//should call backend for subjects
var subjects = [];

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

function setSubjects(resp) {
  var jsob = JSON.parse(JSON.stringify(resp));
  subjects = jsob.categories;
  for (var i = 0; i < subjects.length; i++) {
  	subjects[i] = subjects[i].capitalize();
  }
}
