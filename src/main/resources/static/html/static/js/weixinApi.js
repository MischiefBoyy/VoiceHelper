var appCommon = new AppCommon();
var url = location.href;
var postUrl = window.PRJ_ROOT + "/Jsapi/checkJsapi";
var data = {
	"url" : url
};
var result = null;

$.ajax({
	url : postUrl,
	data : data,
	async : true,
	type : "POST",
	success : function(res) {
		result = JSON.parse(res);
		wx.config({
			debug : false,
			appId : result.appId,
			timestamp : result.timestamp,
			nonceStr : result.noncestr,
			signature : result.signature,
			jsApiList : [ 'hideOptionMenu', 'startRecord', 'stopRecord',
					'onVoiceRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice',
					'onVoicePlayEnd', 'uploadVoice', 'downloadVoice' ]
		})

		wx.ready(function() {

			wx.checkJsApi({
				jsApiList : [ 'hideOptionMenu', 'startRecord', 'stopRecord',
						'onVoiceRecordEnd', 'playVoice', 'pauseVoice',
						'stopVoice', 'onVoicePlayEnd', 'uploadVoice',
						'downloadVoice' ], // 需要检测的JS接口列表，所有JS接口列表见附录2,
				success : function(res) {
					console.log(res); // 以键值对的形式返回，可用的api值true，不可用为false
					// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
				},
				fail : function(res) {
					console.log(res);
				}
			});

			wx.hideOptionMenu();
			console.log("ready");
	          
		})
	}
});

wx.error(function(res) {
	console.log(res);
});