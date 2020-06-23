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
    
    const searchDashboard = () => {
    	
    }
    
    const searchBarChart = () => {
    	const date = $('#datetimePicker').val();
    	
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	
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
           	}
    	});
    }
    
    const searchLiseChart = (type) => { 
    	const date = $('#datetimePicker').val();
    	
    	let param = new Object();
    	param.pointType = type;
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	param.daysDate = moment(date).subtract(9, 'days').format("YYYY-MM-DD 00:00:00");
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search/line",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			let tempChart = null;
    			let waterChart = null;
    			
    			if (type === 'A') {
    				tempChart = LineChart.init("tempALineChart", data.tempALineChart);
    				waterChart = LineChart.init("waterALineChart", data.waterALineChart);
    			} else if (type === 'B') {
    				tempChart = LineChart.init("tempBLineChart", data.tempBLineChart);
    				waterChart = LineChart.init("waterBLineChart", data.waterBLineChart);
    			} else if (type === 'C') {
    				tempChart = LineChart.init("tempCLineChart", data.tempCLineChart);
    				waterChart = LineChart.init("waterCLineChart", data.waterCLineChart);
    			} else if (type === 'D') {
    				tempChart = LineChart.init("tempDLineChart", data.tempDLineChart);
    				waterChart = LineChart.init("waterDLineChart", data.waterDLineChart);
    			} else if (type === 'E') {
    				tempChart = LineChart.init("tempELineChart", data.tempELineChart);
    				waterChart = LineChart.init("waterELineChart", data.waterELineChart);
    			}
    			
    			setTimeout(function () {
    				tempChart.resize();
    				waterChart.resize();
                }, 200);
           	}
    	});
    }
    
    const search = () => {
    	searchDashboard();
    	searchBarChart();
    	searchLiseChart('A');
    }
    
    search();
    
    $('#searchBtn').click(function() {
    	search();
    });
    
    $("#lineChartTab li").click(function() {
    	const type = this.id;
    	searchLiseChart(type);
    });
});
