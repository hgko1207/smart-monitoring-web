moment.locale('ko');

$(document).ready(function() {
	let map;
	let type = 'A';
	
	let barChartA = null;
	let barChartB = null;
	let barChartC = null;
	let barChartD = null;
	let barChartE = null;
	
    $('#datetimePicker').daterangepicker({ 
        singleDatePicker: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
    
    setTimeout(function() {
    	map = GoogleMap.init('googleMap');
    	let site = new Object();
    	site.latitude = 36.5;
		site.longitude = 127.5;
    	
    	GoogleMap.markerAndInfoOverlay(map, site);
	}, 500);
    
    const clearBarChart = () => {
    	if (barChartA != null) {
    		barChartA.clear();
    	}
    	if (barChartB != null) {
    		barChartB.clear();
    	}
    	if (barChartC != null) {
    		barChartC.clear();
    	}
    	if (barChartD != null) {
    		barChartD.clear();
    	}
    	if (barChartE != null) {
    		barChartE.clear();
    	}
    }
    
    const searchBarChart = () => {
    	clearBarChart();
    	
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	const date = $('#datetimePicker').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search/bar",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			if (data.soilABarChart) {
    				barChartA = EchartsBarChart.init("soilAChart", data.soilABarChart);
    			}
    			if (data.soilBBarChart) {
    				barChartB = EchartsBarChart.init("soilBChart", data.soilBBarChart);
    			}
    			if (data.soilCBarChart) {
    				barChartC = EchartsBarChart.init("soilCChart", data.soilCBarChart);
    			}
    			if (data.soilDBarChart) {
    				barChartD = EchartsBarChart.init("soilDChart", data.soilDBarChart);
    			}
    			if (data.soilEBarChart) {
    				barChartE = EchartsBarChart.init("soilEChart", data.soilEBarChart);
    			}
           	}
    	});
    }
    
    const searchDashboard = () => {
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	const date = $('#datetimePicker').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.currentDate = moment(date).format("YYYY-MM-DD hh:mm:ss");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			const weather = data.weatherInfo;
    			$('#weatherDate').html(weather.date);
    			$('#weatherTemp').html(weather.temp);
    			$('#weatherType').html(weather.type);
    			$('#description').html(weather.description);
    			$('#weatherRainfall').html(weather.rainfall);
    			$('#weatherFineDust').html(weather.fineDust);
    			$('#weatherOzone').html(weather.ozone);
           	}
    	});
    }
    
    const searchLiseChart = (type) => {
    	let param = new Object();
    	param.pointType = type;
    	const date = $('#datetimePicker').val();
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	param.daysDate = moment(date).subtract(9, 'days').format("YYYY-MM-DD 00:00:00");
    	param.sensorPoint = $('#sensorPointSelect').val();
    	
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
    
    const search = (type) => {
    	searchBarChart();
    	searchDashboard();
    	searchLiseChart(type);
    }
    
    search(type);
    
    $('#searchBtn').click(function() {
    	search(type);
    });
    
    $("#lineChartTab li").click(function() {
    	type = this.id;
    	searchLiseChart(type);
    });
});
