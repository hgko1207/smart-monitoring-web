var EchartsBarChart = function() {
    var barChartContainer = function(id, data) {
        if (typeof echarts == 'undefined') {
            console.warn('Warning - echarts.min.js is not loaded.');
            return;
        }

        var bars_element = document.getElementById(id);
        if (bars_element) {
            var bars_basic = echarts.init(bars_element);

            bars_basic.setOption({
                textStyle: {
                    fontFamily: 'Roboto, Arial, Verdana, sans-serif',
                    fontSize: 12
                },
                animationDuration: 750,
                grid: {
                    left: 0,
                    right: 5,
                    top: 5,
                    bottom: 0,
                    containLabel: true
                },
                xAxis: [{
                	show: false,
                }],
                yAxis: [{
                    type: 'category',
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
                        show: true,
                        lineStyle: {
                            color: '#eee'
                        }
                    },
                    splitArea: {
                        show: true,
                        areaStyle: {
                            color: ['rgba(250,250,250,0.1)', 'rgba(0,0,0,0.015)']
                        }
                    }
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
    };

    return {
        init: function(id, data) {
        	barChartContainer(id, data);
        }
    }
}();
