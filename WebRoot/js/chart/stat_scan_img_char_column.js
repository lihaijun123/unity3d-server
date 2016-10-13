$(function () {
	initData();

});

function initData(){
	var colors = Highcharts.getOptions().colors;
	var options = {
	        chart: {
	    		renderTo: "container",
	            type: 'column'
	        },
	        title: {
	            text: '识别次数'
	        },
	        xAxis: {
	            categories: []
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '识别(次)'
	            }
	        },
	        legend:{
	        	enabled:false
	        },
	        plotOptions:{
	        	column:{
	        		cursor:"pointer",
	        		point:{
	        			events:{
	        				click:function(){

	        				}
	        			}
	        		}
	        	}
	        },
	        series: [{
	            name: '识别次数',
	            data: [],
	            dataLabels: {
	                enabled: true,
	                rotation: 0,
	                color: '#FFFFFF',
	                align: 'center',
	                format: '{point.y:.1f}', // one decimal
	                y: 10, // 10 pixels down from the top
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        }]
	};
	$.ajax({
		url:"/fs/chart/scanimg/column",
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
			for(var index in data){
				var json = data[index];
				var name = json.pic_name;
				var num = json.invoke_count;
				options.xAxis.categories[index] = name;
				var dataJson = {};
				dataJson.y = num;
				dataJson.name = name;
				dataJson.color = colors[index];
				options.series[0].data[index] = dataJson;
			}
			var chart = new Highcharts.Chart(options);
		},
		error:function(){

		}
	});
}