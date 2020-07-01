var EchartsBarChart = function() {
    var _barsChart = function(id, data) {
        if (typeof echarts == 'undefined') {
            console.warn('Warning - echarts.min.js is not loaded.');
            return;
        }
        
        var bars_basic = null;

        var bars_element = document.getElementById(id);
        if (bars_element) {
            bars_basic = echarts.init(bars_element);
            bars_basic.setOption({
                textStyle: {
                    fontFamily: 'Roboto, Arial, Verdana, sans-serif',
                    fontSize: 12
                },
                animationDuration: 750,
                grid: {
                    left: 10,
                    right: '15%',
                    top: 15,
                    bottom: 0,
                    containLabel: true
                },
                xAxis: [{
                	show: false,
                }],
                yAxis: [{
                    type: 'category',
                    data: data.categories,
                    axisTick: {
                    	show: false,
                    	length: 0
                    },
                    axisLabel: {
                        color: '#333'
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#999',
                            width: 1
                        }
                    },
//                    splitLine: {
//                        show: true,
//                        lineStyle: {
//                            color: '#eee'
//                        }
//                    },
//                    splitArea: {
//                        show: true,
//                        areaStyle: {
//                            color: ['rgba(250,250,250,0.1)', 'rgba(0,0,0,0.015)']
//                        }
//                    }
                }],
                series: data.barChartSeries
            });
        }

        // Resize function
        var triggerChartResize = function() {
        	bars_element && bars_basic.resize();
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
        
        return bars_basic;
    };

    return {
        init: function(id, data) {
        	return _barsChart(id, data);
        }
    }
}();
