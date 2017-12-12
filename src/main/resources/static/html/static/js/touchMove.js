		
var posStart = 0;//初始化起点坐标  
var posEnd = 0;//初始化终点坐标  
var posMove = 0;//初始化滑动坐标  
var timeStart=0;
var timeEnd=0;
//console.log(screen); 

function Aanmite(){
	setTimeout(function(){
		$(".question_text").eq($(".question_text").length-1).css("opacity",".5")
	},400)
	setTimeout(function(){
		$(".question_text").eq($(".question_text").length-1).css("opacity","1")
	},800)
}
function initEvent(btnElem) {  

	 var timer,setInter;
	 //开始录音
     btnElem.on("touchstart", function(e) { 
    	$("#saying p").css({"background":"transparent"})
        $("#saying i").addClass("icon-huatong-copy").removeClass("icon-chexiao")
    	timeStart = new Date();
        e.preventDefault();//阻止浏览器默认行为  
        posStart = 0;  
        posStart = e.originalEvent.targetTouches[0].pageY;//获取起点坐标  
        btnElem.css("background","#ccc")
		$("#saying").show()
        btnElem.val('松开 结束');  
        console.log(timeStart);  
        console.log(posStart+'---------开始坐标');
        //开始录音
        wx.startRecord();
             timer = setTimeout(function(){
           		var str ='<div class="question clearfix">'+
						'<div class="heard_img fr">'+
							'<img src="img/touxiang.jpg" alt="" width="100%">'+
						'</div>'+
						'<div class="question_text fr">'+
							'<p style="width:30px;text-align:right;">&nbsp;</p>'+
						'</div>'+
					'</div>';
					$(".speak_box").append(str)
					$('.question_text>p').css({"max-width":$(document).width()-140,"word-wrap":"break-word","word-break":"normal"})
					clearInterval(setInterval)
					setInter = setInterval(Aanmite,800)
					if ($(document).height()>$(window).height()) {
						$(document).scrollTop($(document).height()-$(window).height())
					}
					//播放、停止录音
			  		$(".question_text").off().on("touchstart",function(){

			  			if ($(this).attr("localId")) {
			  				var lyIndex = $(".question_text").index(this);
			  				console.log(lyIndex);
			  				if ($.cookie('the_cookie')!="null") {
								$(".answer_text").eq($.cookie('the_cookie')).find("audio")[0].pause();
								$(".answer_text").eq($.cookie('the_cookie')).find("audio")[0].currentTime = 0;
								$(".answer_text").eq($.cookie('the_cookie')).find("i.iconfont").show()
					            $(".answer_text").eq($.cookie('the_cookie')).find(".preloader_1").hide()
							}
			  				var setIntervalPlay;
			  				if ($(".question_text").eq(lyIndex).attr("lyzt")==false||$(".question_text").eq(lyIndex).attr("lyzt")=="false") {
			  					
				  				console.log("我有localId----"+$(this).attr("localId")+"-------"+lyIndex)
				  				wx.playVoice({
				  					localId:$(".question_text").eq(lyIndex).attr("localId"),
				  					success:function(){	
				  						$(".question_text").eq(lyIndex).find(".preloader_1").show()
				  						$(".question_text").eq(lyIndex).find("i.iconfont").hide()
				  					}						  				
				  				})
				  				$(".question_text").eq(lyIndex).attr("lyzt","true")
				  				$.cookie('wx_cookie',lyIndex)
//						  				监听播放结束
				  				wx.onVoicePlayEnd({
								    success: function (res) {
								        var localIds = res.localId; // 返回音频的本地ID
								        $(".question_text").eq(lyIndex).find(".preloader_1").hide()
								        $(".question_text").eq(lyIndex).find("i.iconfont").show()
								    }
								});
				  				
				  				
				  				
			  				} else{
			  					wx.stopVoice({
				  					localId:$(".question_text").eq(lyIndex).attr("localId")
				  				})
				  				$(".question_text").eq(lyIndex).find("i.iconfont").show()
				  				$(".question_text").eq(lyIndex).find(".preloader_1").hide()
			  					$(".question_text").eq(lyIndex).attr("lyzt","false")
			  				}
			  				
			  				
			  				
			  			}
			  		})
					
					
					
           		},800)
        		
              

           		
           		
    });  
    //取消录用
    btnElem.on("touchmove", function(e) {  
        e.preventDefault();//阻止浏览器默认行为  
        posMove = 0;  
        posMove = e.originalEvent.targetTouches[0].pageY;//获取滑动实时坐标  
        if(posStart - posMove < 80){  
        	 $("#saying p").css({"background":"transparent"})
             $("#saying i").addClass("icon-huatong-copy").removeClass("icon-chexiao")
             
        }else{  
        	 $("#saying p").css({"background":"#CD2626"})  
             $("#saying i").addClass("icon-chexiao").removeClass("icon-huatong-copy")
             
        }  


    });  
    //结束录音
    btnElem.on("touchend", function(e) {
    	timeEnd = new Date();
    	console.log(timeEnd); 
        e.preventDefault();  
        posEnd = 0;  
        posEnd = e.originalEvent.changedTouches[0].pageY;//获取终点坐标  
        btnElem.css("background","#fff")
        $("#saying").hide()
        btnElem.val('按住 说话');  
//              console.log(posEnd);
		 if (timeEnd-timeStart<1000) {
            	 clearTimeout(timer)
            	 wx.stopRecord();
            	 console.log("录音小于1S")
            	 return;
            }  
        if(posStart - posEnd < 80 ){ 

			//停止录音并上传
        	wx.stopRecord({
                success: function (res) {
                	//保存在本地录音的id
                    var localId = res.localId;
            $(".question_text").eq($(".question_text").length-1).attr("localId",localId).append("<span>"+Math.ceil((timeEnd-timeStart)/1000)+"\"</span>");
        	$(".question_text>p").eq($(".question_text").length-1).css("width",(timeEnd-timeStart)/1000*2+30).html('&nbsp;<i class="iconfont icon-audio-right"></i><p class="preloader_1"><span></span><span></span><span></span><span></span><span></span></p>')
        	clearTimeout(timer)
        	clearInterval(setInter)
                    
                    
                    
                    
                    console.log("----正常录音结束："+localId);
                    wx.uploadVoice({
                        localId: localId, // 需要上传的音频的本地ID，由stopRecord接口获得
                        isShowProgressTips: 1, // 默认为1，显示进度提示
                            success: function (res) {
                            var serverId = res.serverId; // 返回音频的服务器端ID
                            console.log("----上传到微信服务器录音成功："+serverId);
                            
                            //发送临时素材id到自己的后台进行语音解析
                            $.ajax({
                            	url:window.PRJ_ROOT+"/bankQa/voiceQa"	
                            	,type:"POST"
                            	,data:{"mediaId":serverId}
                            	,async:true
                            	,success:function(res){
                            		voiceCallBack(res);
                            	}
                            });
                        }
                    });
                }
            });
        }else{  
            console.log("取消发送");  
            console.log("Cancel");  
            $(".question:last-child").remove()
            clearInterval(setInter)
            //停止并取消录音
            wx.stopRecord({
                success: function (res) {
                    var localId = res.localId;
                    console.log("------取消录音");
                }
            });
        };  
    });  
};  
