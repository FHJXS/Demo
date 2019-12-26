(function() {
	var y = document.body.scrollTop + 1;
	var totalHeight = document.body.scrollHeight;
	var height = totalHeight * 10 + 1;
	var step = 100;
	window.scroll(0, y);
	function f() {
		if (y < height) {
			if (y % height == 0) {
				setTimeout(f, 50);
			}else{
				y += step;
				window.scroll(0, y);
				setTimeout(f, 25);
			}
		} else {
			window.scroll(0, y);
			document.title += "scroll-done";
		}
	}
	setTimeout(f, 1000);
})();