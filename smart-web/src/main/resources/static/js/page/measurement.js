moment.locale('ko');

var MeasurementTable = {
	ele: "#measurementTable",
	table: null,
	option: {
		columns: [
			{ data: "date" },
			{ data: "valueA1" },
			{ data: "valueA2" },
			{ data: "valueA3" },
			{ data: "valueB1" },
			{ data: "valueB2" },
			{ data: "valueB3" },
			{ data: "valueC1" },
			{ data: "valueC2" },
			{ data: "valueC3" },
			{ data: "valueD1" },
			{ data: "valueD2" },
			{ data: "valueD3" },
			{ data: "valueE1" },
			{ data: "valueE2" },
			{ data: "valueE3" },
		]
	},
	init: function() {
		this.table = Datatables.measurement(this.ele, this.option);
	}
}

var WeatherTable = {
	ele: "#weatherTable",
	table: null,
	option: {
		columns: [
			{ data: "date" },
			{ data: "temp" },
			{ data: "hum" },
			{ data: "arvlty" },
			{ data: "afp" },
			{ data: "solradQy" },
			{ data: "soilMitr" },
		]
	},
	init: function() {
		this.table = Datatables.weather(this.ele, this.option);
	}
}

$(document).ready(function() {
	let measurementTable = null;
	let weatherTable = null;
	
	let startDate = new Date();
//	startDate.setDate(startDate.getDate() - 1); 
	let endDate = new Date();
	
    let datePicker = $('.daterange-picker').daterangepicker({
        opens: 'right',
        startDate: startDate,
        endDate: endDate,
        applyClass: 'btn-primary',
        cancelClass: 'btn-light',
        ranges: {
            '오늘': [moment(), moment()],
            '7일전': [moment().subtract(6, 'days'), moment()],
            '30일전': [moment().subtract(29, 'days'), moment()],
            '이번달': [moment().startOf('month'), moment().endOf('month')],
            '지난달': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
        locale: {
            format: 'YYYY/MM/DD',
            customRangeLabel: "날짜 선택",
        }
    },
    function(start, end) {
    	startDate = start;
    	endDate = end;
    });
    
    let lineChart = null;
    MeasurementTable.init();
    WeatherTable.init();
    
    /** 센서타입 선택 시 */
    $('#sensorSelect').change(function() {
    	const val = $(this).val();
    	if (val === '기상') {
    		$('#locationSelectDiv').addClass('d-none');
    		$('#sensorPointSelectDiv').addClass('d-none');
    		$('#weatherSelectDiv').removeClass('d-none');
    	} else {
    		$('#locationSelectDiv').removeClass('d-none');
    		$('#sensorPointSelectDiv').removeClass('d-none');
    		$('#weatherSelectDiv').addClass('d-none');
    	}
    });
    
    const search = () => {
    	if (lineChart != null) {
			lineChart.clear();
		}
    	
    	MeasurementTable.table.clear().draw();
    	WeatherTable.table.clear().draw();
    	
    	measurementTable = null;
    	weatherTable = null;
    	
    	removeSensorSelected();
    	removeWeatherSelected();
    	
    	const sensor = $('#sensorSelect').val();
    	if (sensor == '기상') {
    		let param = new Object();
    		param.weatherType = $('#weatherTypeSelect').val();
    		param.startDate = moment(startDate).local().format("YYYY-MM-DD 00:00:00");
    		param.endDate = moment(endDate).local().format("YYYY-MM-DD 23:59:59");
    		
    		$.ajax({
    			url: contextPath + "/measurement/search/weather",
    			type: "POST",
    			data: JSON.stringify(param),
    			contentType: "application/json",
    			success: function(data) {
    				$('#weatherTableDiv').removeClass('d-none');
    				$('#measurementTableDiv').addClass('d-none');
    				
    				addWeatherSelected(param.weatherType);
    				
    				let chartSeries = data.chartInfo.lineChartSeries;
    				$.each(chartSeries, function(i, val) {
    					val.data = val.dataList.map(data => [data.date, data.value]);
    				});
    				data.chartInfo.lineChartSeries = chartSeries;
    				
    				lineChart = MeasurementChart.init(data.chartInfo);
    				weatherTable = WeatherTable.table.rows.add(data.tableInfos).draw();
    			}
    		}); 
    	} else {
    		let param = new Object();
    		param.sensor = sensor;
    		param.sensorPoint = $('#sensorPointSelect').val();
    		param.location = $('#locationSelect').val();
    		param.startDate = moment(startDate).local().format("YYYY-MM-DD 00:00:00");
    		param.endDate = moment(endDate).local().format("YYYY-MM-DD 23:59:59");
    		
    		$.ajax({
    			url: contextPath + "/measurement/search",
    			type: "POST",
    			data: JSON.stringify(param),
    			contentType: "application/json",
    			success: function(data) {
    				$('#weatherTableDiv').addClass('d-none');
    				$('#measurementTableDiv').removeClass('d-none');
    				
    				changeSensorHeaderName(data.sensor, data.unit);
    				addSensorSelected(param.location);
    				
    				let chartSeries = data.chartInfo.lineChartSeries;
    				$.each(chartSeries, function(i, val) {
    					val.data = val.dataList.map(data => [data.date, data.value]);
    				});
    				data.chartInfo.lineChartSeries = chartSeries;
    				
    				lineChart = MeasurementChart.init(data.chartInfo);
    				measurementTable = MeasurementTable.table.rows.add(data.tableInfos).draw();
    			}
    		}); 
    	}
    }
    
    /** 검색 버튼 클릭 시 */
    $('#searchBtn').click(function() {
    	search();
    });
    
    /** 계측정보 선택(색상) */
    const addSensorSelected = (location) => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	if (location === '상층') {
    		$.each(point, function(i, val) {
    			$('#top' + val).addClass('selected-column');
    		});
    	} else if (location === '중층') {
    		$.each(point, function(i, val) {
    			$('#middle' + val).addClass('selected-column');
    		});
    	} else if (location === '하층') {
    		$.each(point, function(i, val) {
    			$('#bottom' + val).addClass('selected-column');
    		});
    	}
    }
    
    /** 계측정보 선택 초기화 */
    const removeSensorSelected = () => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	$.each(point, function(i, val) {
			$('#top' + val).removeClass('selected-column');
			$('#middle' + val).removeClass('selected-column');
			$('#bottom' + val).removeClass('selected-column');
		});
    }
    
    /** 기상정보 선택(색상) */
    const addWeatherSelected = (type) => {
    	if (type === '기온') {
			$('#weather1').addClass('selected-column');
    	} else if (type === '습도') {
    		$('#weather2').addClass('selected-column');
    	} else if (type === '풍속') {
    		$('#weather3').addClass('selected-column');
    	} else if (type === '강수량') {
    		$('#weather4').addClass('selected-column');
    	} else if (type === '일조량') {
    		$('#weather5').addClass('selected-column');
    	} else if (type === '토양수분') {
    		$('#weather6').addClass('selected-column');
    	}
    }
    
    /** 기상정보 선택 초기화 */
    const removeWeatherSelected = () => {
    	for (let i = 1; i <= 6; i++) {
    		$('#weather' + i).removeClass('selected-column');
    	}
    }
    
    /** 계측정보 테이블 헤더 이름 변경 */
    const changeSensorHeaderName = (sensor, unit) => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	$.each(point, function(i, val) {
			$('#top' + val).html(val + " 상층 " + sensor + "(" + unit + ")");
			$('#middle' + val).html(val + " 중층 " + sensor + "(" + unit + ")");
			$('#bottom' + val).html(val + " 하층 " + sensor + "(" + unit + ")");
		});
    }
    
    search();
    
    $('#saveBtn').click(function() {
    	const sensor = $('#sensorSelect').val();
    	
    	const dateTitle = moment().format("YYYY-MM-DD") + '_' + moment().format("HH") + ':' + moment().format("mm");
    	downloadTitle = sensor + " 데이터_" + dateTitle;
    	
    	if (sensor === '기상') {
    		if (weatherTable != null) {
    			weatherTable.buttons().trigger();
    		}
    	} else {
    		if (measurementTable != null) {
    			measurementTable.buttons().trigger();
    		}
    	}
    });
});