moment.locale('ko');

$(document).ready(function() {
	let map;
	let infowindow = null;
	
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
    	search(type);
	}, 500);
    
    /** 막대그래프 초기화 */
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
    
    /** 막대그래프 데이터 조회 */
    const searchBarChart = () => {
    	clearBarChart();
    	
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	param.sensorPoint = $('#sensorPointSelect').val();
    	const date = $('#datetimePicker').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	param.currentDate = date;
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search/bar",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			if (data.dateTime) {
    				$('#currentDate').html(data.dateTime + " 기준");
    			}
    			
    			$('#barChartTitleA').html(data.sensor + " A");
    			$('#barChartTitleB').html(data.sensor + " B");
    			$('#barChartTitleC').html(data.sensor + " C");
    			$('#barChartTitleD').html(data.sensor + " D");
    			$('#barChartTitleE').html(data.sensor + " E");
    			
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
    
    /** 중간 대시보드 정보 조회 */
    const searchDashboard = () => {
    	if (infowindow != null) {
    		infowindow.close();
    	}
    	
    	let param = new Object();
    	param.sensor = $('#sensorTypeSelect').val();
    	param.sensorPoint = $('#sensorPointSelect').val();
    	const date = $('#datetimePicker').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	param.currentDate = date;
    	
    	$.ajax({
    		url: contextPath + "/dashboard/search",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			const weather = data.weatherInfo;
    			$('#weatherDate').html(weather.date);
    			$('#weatherHour').html(weather.hour);
    			$('#weatherTemp').html(weather.temp);
    			$('#weatherType').html(weather.type);
    			$('#description').html(weather.description);
    			$('#weatherRainfall').html(weather.rainfall);
    			$('#weatherFineDust').html(weather.fineDust);
    			$('#weatherOzone').html(weather.ozone);
    			
    			const info = data.environmentInfo;
    			$('#environmentPoint').html(info.point);
    			$('#environmentDate').html(info.date);
    			$('#environmentCurrent').html(info.current);
    			$('#environmentSensor1').html(info.sensor);
    			$('#environmentSensor2').html(info.sensor);
    			$('#environmentSensor3').html(info.sensor);
    			$('#environmentLevel').html("[" + info.totalLevel + "]");
    			$('#environmentLevel').css('color', info.totalLevelColor);
    			$('#environmentLevel1').html(info.level1 + "단계");
    			$('#environmentLevel1').css('color', info.level1Color);
    			$('#environmentSensor1Day').html(info.level1Day);
    			$('#environmentLevel2').html(info.level2 + "단계");
    			$('#environmentLevel2').css('color', info.level2Color);
    			$('#environmentSensor2Day').html(info.level2Day);
    			$('#environmentLevel3').html(info.level3 + "단계");
    			$('#environmentLevel3').css('color', info.level3Color);
    			$('#environmentSensor3Day').html(info.level3Day);
    			
//    			infowindow = GoogleMap.markerAndInfoOverlay(map, data.mapInfo);
    			infowindow = GoogleMap.markerAndInfoOverlayList(map, data.mapInfos);
           	}
    	});
    }
    
    /** 하단 라인 그래프 데이터 조회 */
    const searchLineChart = (type) => {
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
    	searchLineChart(type);
    }
    
    /** 검색 버튼 클릭 시 */
    $('#searchBtn').click(function() {
    	search(type);
    });
    
    /** 하단 토양계측정보 탭 선택 시 */
    $("#lineChartTab li").click(function() {
    	type = this.id;
    	searchLineChart(type);
    });
    
    /** 하단 토양계측정보 탭 선택 시 */
    $("#mainTab li").click(function() {
    	searchLineChart('A');
    });
});
