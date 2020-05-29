moment.locale('ko');

let map;

$(document).ready(function() {
    $('#datetimePicker').daterangepicker({ 
        singleDatePicker: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
    
    setTimeout(function() {
    	map = GoogleMap.init('googleMap');
    	let site = new Object();
    	site.lat = 36.5;
		site.lon = 127.5;
    	
    	GoogleMap.markerAndInfoOverlay(map, site);
	}, 500);
    
    $.ajax({
		url: contextPath + "/dashboard/search",
		type: "POST",
		data: {},
		contentType: "application/json",
		success: function(data) {
			EchartsBarChart.init("soilABarChart", data.soilABarChart);
			EchartsBarChart.init("soilBBarChart", data.soilBBarChart);
			EchartsBarChart.init("soilCBarChart", data.soilCBarChart);
			EchartsBarChart.init("soilDBarChart", data.soilDBarChart);
			EchartsBarChart.init("soilEBarChart", data.soilEBarChart);
			
			LineChart.init("tempALineChart", data.tempALineChart);
			LineChart.init("waterALineChart", data.waterALineChart);
       	}
	}); 
});
