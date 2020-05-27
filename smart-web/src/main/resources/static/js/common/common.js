/*******************************************************************************************************
 * 위젯 공통 기능
*******************************************************************************************************/
var CommonWidget = function() {
	 // Uniform
    var _componentUniform = function() {
        if (!$().uniform) {
            console.warn('Warning - uniform.min.js is not loaded.');
            return;
        }

        // Initialize
        $('.form-input-styled').uniform({
        	//fileButtonClass: 'action btn bg-blue'
        });
        
        $('.form-check-input-styled').uniform();
    };
    
	var _componentSelect2 = function() {
		if (!$().select2) {
        	console.warn('Warning - select2.min.js is not loaded.');
            return;
        }
    	
        var $select = $('.form-control-select2').select2({
            minimumResultsForSearch: Infinity,
            width: '100%',
            placeholder: 'Select a Data...'
        });
        
        $('.select2-size').select2({
            minimumResultsForSearch: Infinity,
            width: '90'
        });
        
        $('.select-search').select2();
        
        $select.on('change', function() {
            $(this).trigger('blur');
        });
    };
    
    var _componentDaterange = function() {
    	if (!$().daterangepicker) {
            console.warn('Warning - daterangepicker.js is not loaded.');
            return;
        }
    	
    	// Display time picker
        $('.daterange-time').daterangepicker({
            timePicker: true,
            applyClass: 'btn-primary',
            cancelClass: 'btn-light',
            locale: {
                format: 'YYYY-MM-DD HH:00'
            }
        });
    }
    
    return {
        init: function() {
        	_componentUniform();
        	_componentSelect2();
        	_componentDaterange();
        }
    }
}();

document.addEventListener('DOMContentLoaded', function() {
	CommonWidget.init();
});

var swalInit = swal.mixin({
    buttonsStyling: false,
    confirmButtonClass: 'btn btn-primary',
    cancelButtonClass: 'btn btn-light'
});
