const GoogleMap = function() {
	const createMap = function(id) {
		var map = new google.maps.Map(document.getElementById(id), {
			zoom: 10,
			center: new google.maps.LatLng(36.5, 127.5),
			mapTypeId : google.maps.MapTypeId.HYBRID,
			disableDefaultUI: true
		});
		
		google.maps.event.trigger(map, "resize");
		
		return map;
	};
	
	const markerAndInfoOverlay = function(map, data) {
		google.maps.event.trigger(map, "resize");
		
		map.setZoom(20);
		map.setCenter(new google.maps.LatLng(data.latitude, data.longitude));
		
		var marker = new google.maps.Marker({
			position: {lat: data.latitude, lng: data.longitude},
			map: map
		});
		
		var contentString = 
			"<div id='content'>" +
			"<div style='color: #0080FF'><b>" + data.point + " 토양계측정보</b></div>" +
			"<b>토양온도 : " + data.temp1 + "% | " + data.temp2 + "% | " + data.temp3 + "%</b>" + "<br>" +
			"<b>토양수분 : " + data.water1 + "% | " + data.water2 + "% | " + data.water3 + "%</b>" + "<br>" +
			"</div>";
		
		var infowindow = new google.maps.InfoWindow({
			content: contentString
		});
		
		marker.addListener('click', function() {
			infowindow.open(map, marker);
		});
		
		infowindow.open(map, marker);
		
		return infowindow;
	};
	
	const markerAndInfoOverlayList = function(map, list) {
		google.maps.event.trigger(map, "resize");
		
		$.each(list, function(i, data) {
			map.setZoom(22);
			map.setCenter(new google.maps.LatLng(data.latitude, data.longitude));
			
			var marker = new google.maps.Marker({
				position: {lat: data.latitude, lng: data.longitude},
				map: map
			});
			
			var contentString = 
				"<div id='content'>" +
				"<div style='color: #0080FF'><b>" + data.point + " 토양계측정보</b></div>" +
				"<b>토양온도 : " + data.temp1 + "% | " + data.temp2 + "% | " + data.temp3 + "%</b>" + "<br>" +
				"<b>토양수분 : " + data.water1 + "% | " + data.water2 + "% | " + data.water3 + "%</b>" + "<br>" +
				"</div>";
			
			var infowindow = new google.maps.InfoWindow({
				content: contentString
			});
			
			marker.addListener('click', function() {
				infowindow.open(map, marker);
			});
			
			infowindow.open(map, marker);
		});
	}
	
	return {
        init: function(id) {
        	return createMap(id);
        },
        markerAndInfoOverlay: function(map, site) {
        	return markerAndInfoOverlay(map, site);
        },
        markerAndInfoOverlayList: function(map, list) {
        	return markerAndInfoOverlayList(map, list);
        }
    }
}();