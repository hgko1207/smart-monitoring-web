var LineChart = function() {
    var _linesChart = function(id, data) {
        if (typeof echarts == 'undefined') {
            console.warn('Warning - echarts.min.js is not loaded.');
            return;
        }
        
        var line_chart = null;
        
        var line_chart_element = document.getElementById(id);
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
                animationDuration: 500,
                grid: {
                	left: 5,
                    right: 35,
                    top: 35,
                    bottom: 5,
                    containLabel: true
                },
                legend: {
                	left: 'right',
                    itemHeight: 8,
                    itemGap: 20
                },
                tooltip: {
                    trigger: 'axis',
                    backgroundColor: 'rgba(0,0,0,0.55)',
                    padding: [10, 15],
                },
                xAxis: [{
                    type: 'category',
                    boundaryGap: false,
                    data: data.categories,
                    axisLabel: {
                    	color: '#333',
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
                series: data.lineChartSeries,
            });
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

    return {
        init: function(id, data) {
        	return _linesChart(id, data);
        }
    }
}();

var MeasurementChart = function() {
	var _linesChart = function(data) {
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
	           	 	left: 10,
	                right: 40,
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
		            }
	            },
	            xAxis: [{
	                type: 'time',
	                boundaryGap: false,
//	                data: data.categories,
	                axisLabel: {
	                	color: '#333',
	                	fontSize: 13,
		                fontFamily: 'Roboto, sans-serif',
	                    formatter: function (value, idx) {
	                        const date = new Date(value);
	                    	return moment(date).format("MM/DD HH:mm");
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
	                splitNumber: 10,
//	                interval: 1000* 60 * 20,
//	                maxInterval: 1000 * 60 * 60,
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
	
    return {
        init: function(data) {
        	return _linesChart(data);
        }
    }
}();