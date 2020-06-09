moment.locale('ko');

var linesChart = function(data) {
    if (typeof echarts == 'undefined') {
        console.warn('Warning - echarts.min.js is not loaded.');
        return;
    }
    
    var line_chart_element = document.getElementById('lineChart');
    if (line_chart_element) {
        var line_chart = echarts.init(line_chart_element);
        line_chart.setOption({
        	title: {
        		text: data.title,
        		textStyle: {
        			fontSize: 14
        		}
        	},
            textStyle: {
                fontFamily: 'Roboto, Arial, Verdana, sans-serif',
                fontSize: 13
            },
            animationDuration: 750,
            grid: {
           	 	left: 10,
                right: 35,
                top: 35,
                bottom: 60,
               containLabel: true
            },
            legend: {
                itemHeight: 8,
                itemGap: 20
            },
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(0,0,0,0.75)',
                padding: [10, 15],
                textStyle: {
                    fontSize: 13,
                    fontFamily: 'Roboto, sans-serif'
                }
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                data: data.categories,
                axisLabel: {
                    color: '#333'
                },
                axisLine: {
                    lineStyle: {
                        color: '#999'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                }
            }],
            yAxis: [{
                type: 'value',
                axisLabel: {
                    color: '#333'
                },
                axisLine: {
                    lineStyle: {
                        color: '#999'
                    }
                },
                splitLine: {
                    lineStyle: {
                        color: ['#eee']
                    }
                },
                splitArea: {
                    show: true,
                    areaStyle: {
                        color: ['rgba(250,250,250,0.1)', 'rgba(0,0,0,0.01)']
                    }
                }
            }],
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100,
               
            },{
                show: true,
                type: 'slider',
                start: 0,
                end: 100,
                height: 40,
                bottom: 0,
                borderColor: '#ccc',
                fillerColor: 'rgba(0,0,0,0.05)',
                handleStyle: {
                    color: '#585f63'
                }
            }],
            series: data.lineChartSeries,
        }, true);
    }
    
    // Resize function
    var triggerChartResize = function() {
        line_chart_element && line_chart.resize();
    };

    // On sidebar width change
    var sidebarToggle = document.querySelector('.sidebar-control');
    sidebarToggle && sidebarToggle.addEventListener('click', triggerChartResize);

    // On window resize
    var resizeCharts;
    window.addEventListener('resize', function() {
        clearTimeout(resizeCharts);
        resizeCharts = setTimeout(function () {
            triggerChartResize();
        }, 200);
    });
};

var measurementTable = {
	ele: "#measurementTable",
	table: null,
	option: {
		columns: [
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
			{ data: "" },
		]
	},
	init: function() {
		this.table = Datatables.order(this.ele, this.option, 0);
	}
}

$(document).ready(function() {
	measurementTable.init();
	
	let startDate = new Date(moment().format("YYYY-MM-DD 00:00:00"));
	startDate.setDate(startDate.getDate() - 6); 
	let endDate = new Date(moment().format("YYYY-MM-DD 23:59:59"));
	
    $('.daterange-picker').daterangepicker({
        opens: 'left',
        startDate: startDate,
        endDate: endDate,
        applyClass: 'btn-primary',
        cancelClass: 'btn-light',
        locale: {
            format: 'YYYY/MM/DD'
        }
    },
    function(start, end) {
    	startDate = start.format("YYYY-MM-DD 00:00:00");
    	endDate = start.format("YYYY-MM-DD 23:59:59");
    });
    
    $('#searchBtn').click(function() {
    	let param = new Object();
    	param.point = $('#pointSelect').val();
    	param.location = $('#locationSelect').val();
    	param.sensor = $('#sensorSelect').val();
    	param.type = $('#typeSelect').val();
    	param.startDate = startDate;
    	param.endDate = endDate;
    	
    	$.ajax({
    		url: contextPath + "/measurement/search/chart",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			console.log(data);
    			linesChart(data);
           	}
    	}); 
    	
    	measurementTable.table.clear().draw();
    	
    	$.ajax({
    		url: contextPath + "/measurement/search/table",
    		type: "POST",
    		data: JSON.stringify(param),
    		contentType: "application/json",
    		success: function(data) {
    			measurementTable.table.rows.add(data).draw();
           	}
    	}); 
    });
});