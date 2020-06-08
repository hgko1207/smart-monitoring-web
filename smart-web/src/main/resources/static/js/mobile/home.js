$(document).ready(function() {
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
       	}
	}); 
});
