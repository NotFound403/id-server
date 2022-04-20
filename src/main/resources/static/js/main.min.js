;

jQuery( function() {
    
    // 提示
	if($('[data-toggle="tooltip"]')[0]) {
		$('[data-toggle="tooltip"]').tooltip({
			"container" : 'body',
		});
	}
    
    // 弹出框
    if($('[data-toggle="popover"]')[0]) {
        $('[data-toggle="popover"]').popover();
    }
    
    // 标签
	$('.js-tags-input').each(function() {
        var $this = $(this);
        $this.tagsInput({
			height: $this.data('height') ? $this.data('height') : '38px',
			width: '100%',
			defaultText: $this.attr("placeholder"),
			removeWithBackspace: true,
			delimiter: [',']
		});
    });
    
    // 时间选择
	jQuery('.js-datetimepicker').each(function() {
		var $input = jQuery(this);
		$input.datetimepicker({
			format: $input.data('format') ? $input.data('format') : false,
			useCurrent: $input.data('use-current') ? $input.data('use-current') : false,
			locale: moment.locale('' + ($input.data('locale') ? $input.data('locale') : '') + ''),
			showTodayButton: $input.data('show-today-button') ? $input.data('show-today-button') : false,
			showClear: $input.data('show-clear') ? $input.data('show-clear') : false,
			showClose: $input.data('show-close') ? $input.data('show-close') : false,
			sideBySide: $input.data('side-by-side') ? $input.data('side-by-side') : false,
			inline: $input.data('inline') ? $input.data('inline') : false,
		});
	});
    
    // 日期选择
	jQuery('.js-datepicker').each(function() {
        var options = {
			weekStart: 1,
			autoclose: typeof($(this).data('auto-close')) != 'undefined' ? $(this).data('auto-close') : true,
            language: 'zh-CN',  // 默认简体中文
            multidateSeparator: ', ', // 默认多个日期用,分隔
            format: $(this).data('date-format') ? $(this).data('date-format') : 'yyyy-mm-dd',
        };
        
        if ( $(this).prop("tagName") != 'INPUT' ) {
            options.inputs = [$(this).find('input:first'), $(this).find('input:last')];
        }
  
        $(this).datepicker(options);
	});
    
    // 颜色选取
	jQuery('.js-colorpicker').each(function() {
		var $colorpicker = jQuery(this);
		var $colorpickerMode = $colorpicker.data('colorpicker-mode') ? $colorpicker.data('colorpicker-mode') : 'hex';
		var $colorpickerinline = $colorpicker.data('colorpicker-inline') ? true: false;
		$colorpicker.colorpicker({
			'format': $colorpickerMode,
			'inline': $colorpickerinline
		});
	});
  
    // 复选框全选
	$("#check-all").change(function () {
        if ($boxname = $(this).data('name')) {
            $(this).closest('table').find("input[name='" + $boxname + "']").prop('checked', $(this).prop("checked"));
        } else {
            $(this).closest('table').find(".lyear-checkbox input[type='checkbox']").prop('checked', $(this).prop("checked"));
        }
	});
	
	// iframe打开tab
    $(document).on('click', '.js-create-tab', function() {
 	    parent.$(parent.document).data('multitabs').create({
 	        iframe : true,
 	        title : $(this).data('title') ? $(this).data('title') : '标题',
 	        url : $(this).data('url') ? $(this).data('url') : 'lyear-main.html'
 	    }, true);
 	});
});