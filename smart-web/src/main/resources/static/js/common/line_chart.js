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
                	 left: 15,
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
                    	color: '#333',
                        formatter: function (value, idx) {
                        	const date = new Date(value);
                        	const result = moment(date).format("MM/DD");
                            return idx === 0 ? moment(date).format("MM/DD hh") : result;
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
                // Zoom control
//                dataZoom: [{
//                        type: 'inside',
//                        start: 0,
//                        end: 100
//                    },{
//                        show: true,
//                        type: 'slider',
//                        start: 0,
//                        end: 100,
//                        height: 40,
//                        bottom: 0,
//                        borderColor: '#ccc',
//                        fillerColor: 'rgba(0,0,0,0.05)',
//                        handleStyle: {
//                            color: '#585f63'
//                        }
//                    }
//                ],
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