//@author: aguler

//Make navigation bar sticky on scroll

$(document).ready(function() {
	var navOff = $("nav").offset().top;
	$("#header-wrapper").height(jQuery("nav").outerHeight());

	$(window).scroll(function() {

		var scrollPos = jQuery(window).scrollTop();

		if (scrollPos >= navOff) {
			$("nav").addClass("sticky");

		}else {
			$("nav").removeClass("sticky");


		}
	});

});