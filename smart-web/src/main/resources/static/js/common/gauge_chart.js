var soilWaterChart = function(id, data) {
	const dataSource = {
			chart: {
				manageresize: "1",
				origw: "350",
				origh: "150",
				palette: "1",
				bgcolor: "FFFFFF",
				lowerlimit: 0,
				upperlimit: 100,
				numbersuffix: "",
				showborder: "0",
				basefontcolor: "000000",
				charttopmargin: "-10",
				chartbottommargin: "0",
				gaugefillmix: "{dark-10},{light-80},{dark-10}",
				gaugefillratio: "3",
				pivotradius: "8",
				gaugeouterradius: "120",
				gaugeinnerradius: "60%",
				gaugeoriginx: "175",
				gaugeoriginy: "150",
				showTickMarks: "0",
				showTickValues: "0",
				majorTMThickness: "0",
				minorTMThickness: "0"
			},
	    colorrange: {
	    	color: [{
	    		minvalue: "0",
			    maxvalue: "50",
			    code: "#F2726F"
	    	},{
			    minvalue: "50",
			    maxvalue: "75",
			    code: "#FFC533"
			},{
		        minvalue: "75",
		        maxvalue: "100",
		        code: "#62B58F"
			}]
	    },
	    dials: {
	    	dial: [{
	    		value: 81,
	    		rearextension: 15,
	        	basewidth: 10
		    }]
	    },
	    trendPoints: {
			point: [{
				startValue: 0,  
				showBorder: 0,
				displayValue: 0
			},{
				startValue: 50,  
				showBorder: 0,
				displayValue: 50
			},{
				startValue: 75,                        
				showBorder: 0,
				displayValue: 75
			},{
				startValue: 100,                        
				showBorder: 0,
				displayValue: 100
			}]
	    }
	};

	FusionCharts.ready(function() {
		var myChart = new FusionCharts({
			type: "angulargauge",
			renderAt: id,
			width: "100%",
			height: "100%",
			dataFormat: "json",
			dataSource
		}).render();
	});
};

var soilTempChart = function(id, data) {
	
};
