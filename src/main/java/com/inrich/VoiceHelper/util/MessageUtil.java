package com.inrich.VoiceHelper.util;

import java.io.File;

public class MessageUtil {
	
	/**
	 * 从WX服务器下载临时语音素材
	 */
	public static final String URL_DOWNLOAD_VOICE="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	/**
	 * 从WX服务器获取jsapi
	 */
	public static final String URL_JSAPI_TOKEN="https://api.weixin.qq.com/cgi-bin/ticket/getticket";

	/**
	 * 客服发送消息的接口
	 */
	public static final String URL_SEND_KF = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	/**
	 * 获取token的get请求url
	 */
	public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	/**
	 * APPID
	 */
	public static final String WX_APPID = "wxb7b205ffe5a9e062";
	/**
	 * appsecret
	 */
	public static final String WX_APPSECRET = "baebdb4ef8ec421825c653ea5ea41b42";
	/**
	 * 保存微信下载的临时录音文件
	 */
	//public static final String VOICE_DOWNLOAD_PATH = "D:"+File.separator+"wx"+File.separator;
	/**
	 * 将从WX下载的语音 经过ffmpeg转换成 FX要求的录音格式
	 */
	//public static final String VOICE_WXTOXF_PATH = "D:"+File.separator+"wxtoxf"+File.separator;
	
	/**
	 * XF 根据文字合成的录音,合成的录音格式为pcm
	 */
	//public static final String VOICE_XF_COMPOSE = "D:"+File.separator+"xfpcm"+File.separator;
	/**
	 * 讲XF合成的pcm音频转换成MP3格式
	 */
	//public static final String VOICE_PCM2MP3 = "D:"+File.separator+"pcm2mp3"+File.separator;
	
	/**
	 * ffmpeg.exe软件所在的位置路径
	 */
	//public static final String FFMPEG_PATH="F:"+File.separator+"ffmpeg"+File.separator+"bin"+File.separator;
	
	/**
	 * XF 的文本语意接口
	 */
	public static final String URL_VOICESEMANTIC_PATH="http://api.xfyun.cn/v1/aiui/v1/voice_semantic";
	/**
	 * XF 的app key
	 */
	public static final String XF_APP_KEY="0237e7c24b1c495592e80ef24ec491a7";
	/**
	 * XF 的app ID
	 */
	public static final String XF_APP_ID="59c47ac8";
	
	/**
	 * 文本语意解析
	 */
	public static final String URL_TEXTSEMANTIC_PATH="http://api.xfyun.cn/v1/aiui/v1/text_semantic";
	/**
	 * ACCESSTOKEN 有效时间7200秒
	 */
	public static String ACCESS_TOKEN = "";
	
	/**
	 * jsApiToken 有效时间7200秒
	 */
	public static String JSAPI_TOKEN = "";

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 返回消息类型：图片
	 */
	public static final String RESP_MESSAGE_TYPE_Image = "image";

	/**
	 * 返回消息类型：语音
	 */
	public static final String RESP_MESSAGE_TYPE_Voice = "voice";

	/**
	 * 返回消息类型：视频
	 */
	public static final String RESP_MESSAGE_TYPE_Video = "video";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：视频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型：VIEW(自定义菜单URl视图)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";

	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	/**
	 * 事件类型：LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";
	/**
	 * 介绍公司的业务
	 */
	public static final int IS_INRICH=0;
	/**
	 * 当前查询的为一级业务
	 */
	public static final int IS_BUSINESS=1;
	/**
	 * 当前查询的为二级业务
	 */
	public static final int IS_CHILD=2;

}
