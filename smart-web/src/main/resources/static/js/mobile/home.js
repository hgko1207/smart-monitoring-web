moment.locale('ko');

$(document).ready(function() {
	$('#datetimePicker').daterangepicker({ 
        singleDatePicker: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
	
	const searchBarChart = () => {
		let param = new Object();
		param.sensor = $('#sensorTypeSelect').val();
    	param.sensorPoint = $('#sensorPointSelect').val();
    	const date = $('#datetimePicker').val();
    	param.startDate = moment(date).format("YYYY-MM-DD 00:00:00");
    	param.endDate = moment(date).format("YYYY-MM-DD 23:59:59");
    	
		$.ajax({
			url: contextPath + "/m/search",
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(data) {
				
			}
		}); 
	}
	
	const searchDashboard = () => {
		
	}
	 
	$('#searchBtn').click(function() {
		searchBarChart();
		searchDashboard();
	});

});
