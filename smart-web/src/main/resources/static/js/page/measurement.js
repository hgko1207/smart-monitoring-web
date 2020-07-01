moment.locale('ko');

var linesChart = function(data) {
    if (typeof echarts == 'undefined') {
        console.warn('Warning - echarts.min.js is not loaded.');
        return;
    }
    
    var line_chart = null;
    
    var line_chart_element = document.getElementById('lineChart');
    if (line_chart_element) {
        line_chart = echarts.init(line_chart_element);
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
           	 	left: 25,
                right: 30,
                top: 35,
                bottom: 5,
               containLabel: true
            },
            legend: {
                itemHeight: 8,
                itemGap: 20
            },
            tooltip: {
                trigger: 'axis',
                backgroundColor: 'rgba(0,0,0,0.55)',
                padding: [10, 15],
                textStyle: {
            		fontSize: 13,
	                fontFamily: 'Roboto, sans-serif'
	            },
//                axisPointer: {
//                    type: 'cross',
//                    animation: false,
//                    label: {
//                    	backgroundColor: '#505765'
////                        backgroundColor: '#ccc',
////                        borderColor: '#aaa',
////                        borderWidth: 1,
////                        shadowBlur: 0,
////                        shadowOffsetX: 0,
////                        shadowOffsetY: 0,
////                        color: '#222'
//                    }
//                },
//                formatter: function (params) {
//                    return params[2].name + '<br />' + ((params[2].value - base) * 100).toFixed(1) + '%';
//                }
            },
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                data: data.categories,
                axisLabel: {
                	color: '#333',
                	fontSize: 13,
	                fontFamily: 'Roboto, sans-serif',
                    formatter: function (value, idx) {
                        var date = new Date(value);
                        return idx === 0 ? value : [date.getMonth() + 1, date.getDate()].join('-');
                    }
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
                min: function (value) {
                	const result = Math.round(value.min - 1);
                    return result <= 0 ? 0 : result;
                },
                axisLabel: {
                	color: '#333',
                	fontSize: 13,
	                fontFamily: 'Roboto, sans-serif',
                    formatter: function (val) {
                        return val + data.unit;
                    }
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
//            dataZoom: [{
//                type: 'inside',
//                start: 0,
//                end: 100,
//            },{
//                show: true,
//                type: 'slider',
//                start: 0,
//                end: 100,
//                height: 40,
//                bottom: 0,
//                borderColor: '#ccc',
//                fillerColor: 'rgba(0,0,0,0.05)',
//                handleStyle: {
//                    color: '#585f63'
//                }
//            }],
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
    
    return line_chart;
};

var measurementTable = {
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

$(document).ready(function() {
	let startDate = new Date(moment().format("YYYY-MM-DD 00:00:00"));
	startDate.setDate(startDate.getDate() - 6); 
	let endDate = new Date(moment().format("YYYY-MM-DD 23:59:59"));
	
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
    	startDate = start.format("YYYY-MM-DD 00:00:00");
    	endDate = end.format("YYYY-MM-DD 23:59:59");
    });
    
    let lineChart = null;
    measurementTable.init();
    
    $('#sensorSelect').change(function() {
    	const val = $(this).val();
    	if (val === '기상') {
    		
    	} else {
    		
    	}
    });
    
    /** 검색 버튼 클릭 시 */
    $('#searchBtn').click(function() {
    	if (lineChart != null) {
			lineChart.clear();
		}
    	
    	const sensor = $('#sensorSelect').val();
    	if (sensor == '기상') {
    		let param = new Object();
    		param.startDate = startDate;
    		param.endDate = endDate;
    		
    	} else {
    		measurementTable.table.clear();
    		removeSelected();
    		
    		let param = new Object();
    		param.sensor = sensor;
    		param.sensorPoint = $('#sensorPointSelect').val();
    		param.location = $('#locationSelect').val();
    		param.startDate = startDate;
    		param.endDate = endDate;
    		
    		$.ajax({
    			url: contextPath + "/measurement/search",
    			type: "POST",
    			data: JSON.stringify(param),
    			contentType: "application/json",
    			success: function(data) {
    				let chartSeries = data.chartInfo.lineChartSeries;
    				$.each(chartSeries, function(i, val) {
    					val.data = val.dataList.map(data => [data.date, data.value]);
    				});
    				data.chartInfo.lineChartSeries = chartSeries;
    				
    				lineChart = linesChart(data.chartInfo);

    				changeHeaderName(data.sensor);
    				addSelected(param.location);
    				measurementTable.table.rows.add(data.tableInfos).draw();
    			}
    		}); 
    	}
    });
    
    const addSelected = (location) => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	if (location == '상층') {
    		$.each(point, function(i, val) {
    			$('#top' + val).addClass('selected-column');
    		});
    	} else if (location == '중층') {
    		$.each(point, function(i, val) {
    			$('#middle' + val).addClass('selected-column');
    		});
    	} else if (location == '하층') {
    		$.each(point, function(i, val) {
    			$('#bottom' + val).addClass('selected-column');
    		});
    	}
    }
    
    const removeSelected = () => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	$.each(point, function(i, val) {
			$('#top' + val).removeClass('selected-column');
			$('#middle' + val).removeClass('selected-column');
			$('#bottom' + val).removeClass('selected-column');
		});
    }
    
    /** 계측정보 테이블 헤더 이름 변경 */
    const changeHeaderName = (sensor) => {
    	const point = ['A', 'B', 'C', 'D', 'E'];
    	$.each(point, function(i, val) {
			$('#top' + val).html(val + " 상층 " + sensor);
			$('#middle' + val).html(val + " 중층 " + sensor);
			$('#bottom' + val).html(val + " 하층 " + sensor);
		});
    }
});