//@author: aguler

var PORTNR = "8080";
var IP = "localhost";

function searchRequest(b) {
  this.portnr = b.portnr;
  this.type = b.type;
  this.top = b.top;
  this.amount = b.amount;
  this.category = b.category;
  this.label = b.label;
  this.from = b.from;
  this.to = b.to;
}

searchRequest.prototype = {
  getReq: function() {
    var vars = "";
    for (var name in this) {
      if (typeof(this[name])==='string') {
        vars += this[name] + "/";
      }
    }

    return vars.substring(0, vars.length -1);
   }
}

function Builder() {
  this.portnr;
  this.type;
  this.top;
  this.amount;
  this.category;
  this.label;
  this.from;
  this.to;

}

Builder.prototype = {
  portnr: function(nr) {
    this.portnr = "http://"+IP+":" + nr;
    return this;
  },
  type: function(t) {
    this.type = t;
    return this;
  },
  top: function(to) {
    this.top = to;
    return this;
  },
  amount: function(a) {
    this.amount = a;
    return this;
  },
  category: function(c) {
    this.category = c;
    return this;

  },
  label: function(l) {
    this.label = l;
    return this;
  },
  from: function(f) {
    this.from = "from/" + f;
    return this;
  },
  to: function(t) {
    this.to = "to/" + t;
    return this;
  }
}
//Default
function getURL() {
  return new searchRequest(new Builder().portnr(PORTNR).type("mot").category("Skatt").label("automatic").from("2015-03-28").to("2015-05-01")).getReq();
}

function getURLInteresting(amount) {
  return new searchRequest(new Builder().portnr(PORTNR).type("mot").top("top").amount(amount)).getReq();
}

function getURLCategories() {
  return new searchRequest(new Builder().portnr(PORTNR).type("categories")).getReq();
}
function getURLSearch(from,to, category) {
  return new searchRequest(new Builder().portnr(PORTNR).type("mot").category(category).label("automatic").from(from).to(to)).getReq();
}



