moment.locale('ko');

$(document).ready(function() {
	let map;
	
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
    
    searchDashboard();
    
    function searchDashboard() {
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	param.date = $('#datetimePicker').val();
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search",
    		type: "POST",
    		data: JSON.stringify(param),
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
    }
    
    $('#searchBtn').click(function() {
    	searchDashboard();
    });
});
