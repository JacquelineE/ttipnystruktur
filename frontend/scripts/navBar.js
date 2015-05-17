//@author: aguler

/*Document scroll on nav-dropdown click*/

function navInit() {
	var ani = 800;

	$("#0").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont0").offset().top
	    }, ani);
	});

	$("#1").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont1").offset().top
	    }, ani);
	});

	$("#2").click(function() {
	    $('html, body').animate({
	        scrollTop: $(".chartcont2").offset().top
	    }, ani);
	});
}