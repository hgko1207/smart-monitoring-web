$(document).ready(function() {
	let map;
	
	const search = (map) => {
		$.ajax({
    		url: contextPath + "/map/search",
    		type: "GET",
    		success: function(response) {
    			GoogleMap.markerAndInfoOverlayList(map, response);
           	}
    	});
	}
	
	setTimeout(function() {
		map = GoogleMap.init('googleMap');
		search(map);
	}, 500);
});