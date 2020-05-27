moment.locale('ko');

$(document).ready(function() {
    $('#datetimePicker').daterangepicker({ 
        singleDatePicker: true,
        locale: {
            format: 'YYYY-MM-DD'
        }
    });
    
    setTimeout(function() {
		GoogleMap.init('googleMap');
	}, 500);
});
