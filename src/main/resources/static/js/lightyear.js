var lightyear = function(){
    /**
     * 页面小提示
     * @param $msg 提示信息
     * @param $type 提示类型:'info', 'success', 'warning', 'danger'
     * @param $delay 毫秒数，例如：1000
     * @param $icon 图标，例如：'fa fa-user' 或 'glyphicon glyphicon-warning-sign'
     * @param $from 'top' 或 'bottom'
     * @param $align 'left', 'right', 'center'
     * @author CaiWeiMing <314013107@qq.com>
     */
    var tips = function ($msg, $type, $delay, $icon, $from, $align) {
        $type  = $type || 'info';
        $delay = $delay || 1000;
        $from  = $from || 'top';
        $align = $align || 'center';
        $enter = $type == 'danger' ? 'animated shake' : 'animated fadeInUp';

        jQuery.notify({
            icon: $icon,
            message: $msg
        },
        {
            element: 'body',
            type: $type,
            allow_dismiss: true,
            newest_on_top: true,
            showProgressbar: false,
            placement: {
                from: $from,
                align: $align
            },
            offset: 20,
            spacing: 10,
            z_index: 10800,
            delay: $delay,
            //timer: 1000,
            animate: {
                enter: $enter,
                exit: 'animated fadeOutDown'
            }
        });
    };
	
	return {
        // 页面小提示
        notify  : function ($msg, $type, $delay, $icon, $from, $align) {
            tips($msg, $type, $delay, $icon, $from, $align);
        },
        // 页面加载动画
		loading : function ($mode) {
		    pageLoader($mode);
		}
    };
}();