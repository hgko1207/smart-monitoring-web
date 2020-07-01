/**
 * 테이블 관련 유틸
 */
$.extend( $.fn.dataTable.defaults, {
	autoWidth: false,
    dom: '<"datatable-header"fl><"datatable-scroll-wrap"t><"datatable-footer"ip>',
    language: {
		emptyTable: '데이터가 없습니다.',
		infoEmpty: "",
		info: " _TOTAL_ 개의 데이터가 있습니다.",
	    search: '<span>검색 :</span> _INPUT_',
	    searchPlaceholder: '내용 입력...',
	    lengthMenu: '<span>Show:</span> _MENU_',
	    paginate: { 'first': 'First', 'last': 'Last', 
	    	'next': $('html').attr('dir') == 'rtl' ? '&larr;' : '&rarr;', 'previous': $('html').attr('dir') == 'rtl' ? '&rarr;' : '&larr;' }
	},
	searching: false,
	lengthChange: false,
	pageLength: 10
	
});

var Datatables = {
	basic: function(id, tableOption, info) {
		var table = $(id).DataTable({
			language: {
				info: info ? info : " _TOTAL_ 개의 데이터가 있습니다." 
			},
			columns: tableOption.columns,
			order: [[0, 'asc']]
		});
		
		return table;
	},
	measurement: function(id, tableOption) {
		var table = $(id).DataTable({
		    columns: tableOption.columns,
		    columnDefs: [
		    	{ orderable: true, className: 'reorder', targets: 0 },
		    	{ orderable: false, targets: '_all' }
		    ],
		    order: [[0, 'desc']],
		    rowCallback: function(row, data, displayIndex, displayIndexFull) {
		    	const location = $('#locationSelect').val();
		    	if (location === '상층') {
			        $(row).children().eq(1).addClass('selected-column');
			        $(row).children().eq(4).addClass('selected-column');
			        $(row).children().eq(7).addClass('selected-column');
			        $(row).children().eq(10).addClass('selected-column');
			        $(row).children().eq(13).addClass('selected-column');
		    	} else if (location === '중층') {
		    		$(row).children().eq(2).addClass('selected-column');
			        $(row).children().eq(5).addClass('selected-column');
			        $(row).children().eq(8).addClass('selected-column');
			        $(row).children().eq(11).addClass('selected-column');
			        $(row).children().eq(14).addClass('selected-column');
		    	} else if (location === '하층') {
		    		$(row).children().eq(3).addClass('selected-column');
			        $(row).children().eq(6).addClass('selected-column');
			        $(row).children().eq(9).addClass('selected-column');
			        $(row).children().eq(12).addClass('selected-column');
			        $(row).children().eq(15).addClass('selected-column');
		    	}
		    }
		});
		
		return table;
	},
	weather: function(id, tableOption) {
		var table = $(id).DataTable({
		    columns: tableOption.columns,
		    columnDefs: [
		    	{ orderable: true, className: 'reorder', targets: 0 },
		    	{ orderable: false, targets: '_all' }
		    ],
		    order: [[0, 'desc']],
		    rowCallback: function(row, data, displayIndex, displayIndexFull) {
		    	const weather = $('#weatherTypeSelect').val();
		    	if (weather === '기온') {
			        $(row).children().eq(1).addClass('selected-column');
		    	} else if (weather === '습도') {
		    		$(row).children().eq(2).addClass('selected-column');
		    	} else if (weather === '풍속') {
		    		$(row).children().eq(3).addClass('selected-column');
		    	} else if (weather === '강수량') {
		    		$(row).children().eq(4).addClass('selected-column');
		    	} else if (weather === '일조량') {
		    		$(row).children().eq(5).addClass('selected-column');
		    	} else if (weather === '토양수분') {
		    		$(row).children().eq(6).addClass('selected-column');
		    	}
		    }
		});
		
		return table;
	},
	download: function(id, tableOption, info, visible, exportColumns) {
		var table = $(id).DataTable({
			dom: '<"datatable-header"fl><"datatable-scroll-wrap"t><"datatable-footer"iBp>',
			language: {
				info: info ? info : " _TOTAL_ 개의 데이터가 있습니다."
			},
			columns: tableOption ? tableOption.columns : null,
			columnDefs: [
				{ orderable: true, className: 'reorder', targets: 0 },
		    	{ orderable: false, targets: '_all' },
				{ visible: false, targets: visible }
			],
			buttons: {
		        buttons: [{
	                extend: 'excelHtml5',
	                className: 'btn bg-primary-400 ml-3',
                    text: '<i class="icon-folder-download mr-2"></i> 다운로드',
                    fieldSeparator: '\t',
		            exportOptions: {
		                columns: exportColumns
		            }
	            }]
		    },
			order: [[0, 'asc']]
		});
		
		return table;
	},
	rowsAdd: function(table, url, param) {
		table.clear().draw();
		
		$.ajax({
			url: url,
			type: "POST",
			data: JSON.stringify(param),
			contentType: "application/json",
			success: function(data) {
				table.rows.add(data).draw();
		   	}
		});
	},
	refresh: function(table, data) {
		table.clear().draw();
		table.rows.add(data).draw();
	}
}
