$(document).ready(function() {
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
		const map = GoogleMap.init('googleMap');
		search(map);
	}, 500);
});