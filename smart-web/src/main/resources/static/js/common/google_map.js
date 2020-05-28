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
	
	const markerAndInfoOverlay = function(map, site) {
		google.maps.event.trigger(map, "resize");
		
		var latlon = {lat: site.lat, lng: site.lon};
		
		map.setZoom(14);
		map.setCenter(new google.maps.LatLng(site.lat, site.lon));
		
		var marker = new google.maps.Marker({
			position: latlon,
			map: map
		});
		
		var contentString = 
			"<div id='content'>" +
			"<div style='color: #0080FF'><b>A 지점 토양계측정보</b></div>" + 
			"<b>토양온도 : 16.4% | 15.2% | 14.2%</b>" + "<br>" + 
			"<b>토양습도 : 21.8% | 27.3% | 22.9%</b>" + "<br>" + 
			"</div>";
		
		var infowindow = new google.maps.InfoWindow({
			content: contentString
		});
		
		marker.addListener('click', function() {
			infowindow.open(map, marker);
		});
		
		infowindow.open(map, marker);
	};
	
	const markerAndInfoOverlayList = function(map, siteSensorList) {
		google.maps.event.trigger(map, "resize");
		
		$.each(siteSensorList, function(key) {
			var site = siteSensorList[key];
			var latlon = {lat: site.lat, lng: site.lon};
			
			map.setZoom(14);
			map.setCenter(new google.maps.LatLng(site.lat, site.lon));
			
			var marker = new google.maps.Marker({
				position: latlon,
				map: map
			});
			
			var contentString;
			if (site.soilSensorLog == null) {
				contentString = 
					"<div id='content'>" +
					"<span style='color: #0080FF'><b>" + site.name + "(토양계측)</b></span><br>" + 
					"</div>";
			} else {
				contentString = 
					"<div id='content'>" +
					"<span style='color: #0080FF'><b>" + site.name + "(토양계측)</b></span><br>" + 
					"<b>표층 : " + site.soilSensorLog.soilSurfaceTemp + "도 | " + site.soilSensorLog.soilSurfaceMoisture + "% | " + site.soilSensorLog.soilSurfaceSalt + "ds/m</b>" + "<br>" + 
					"<b>심층 : " + site.soilSensorLog.soilDeepTemp + "도 | " + site.soilSensorLog.soilDeepMoisture + "% | " + site.soilSensorLog.soilDeepSalt + "ds/m</b>" + 
					"</div>";
			}
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
        }
    }
}();