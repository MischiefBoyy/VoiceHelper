package com.inrich.VoiceHelper.service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inrich.VoiceHelper.mapper.TokenMapper;
import com.inrich.VoiceHelper.model.TokenInfo;
import com.inrich.VoiceHelper.util.DatetimeUtil;
import com.inrich.VoiceHelper.util.HttpUtils;
import com.inrich.VoiceHelper.util.MessageUtil;

@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private TokenMapper tokenMapper;
	
	
	/**
	 * 更新静态AccessToken的值
	 */
	@Override
	public String getAccessToken() {
		String name="accessToken";
		Map<String, Object> tokenMap = tokenMapper.get(name);
		

		if (tokenMap == null) {
			// 访问微信服务器，获取accesstoken，然后插入数据库
			Map<String, String> accessTokenMap = httpGetAccessToken();
			if (accessTokenMap == null) {
				return null;
			}
			TokenInfo tokenInfo = new TokenInfo();
			tokenInfo.setName(name); 
			tokenInfo.setToken(accessTokenMap.get("access_token"));
			tokenInfo.setCreateTime(DatetimeUtil.toDateTimeStr(new Date()));
			tokenInfo.setUpdateTime(DatetimeUtil.toDateTimeStr(new Date()));
			tokenMapper.add(tokenInfo);
			MessageUtil.ACCESS_TOKEN = accessTokenMap.get("access_token");
			System.out.println("accesstoken----------添加完成");
		}

		String accessToken = tokenMap.get("tokenVaule").toString();
		long tsf = (long) tokenMap.get("tsf");
		if (tsf > 6500) {// accesstoken的有效时间为7200秒
			// 时间超过有效时间，需要连接微信服务器重新获得
			Map<String, String> accessTokenMap = httpGetAccessToken();
			if (accessTokenMap == null) {
				return null;
			}
			//更新数据库
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("tokenValue", accessTokenMap.get("access_token"));
			updateMap.put("updateTime", DatetimeUtil.toDateTimeStr(new Date()));
			updateMap.put("tokenName", name);
			tokenMapper.update(updateMap);
			MessageUtil.ACCESS_TOKEN = accessTokenMap.get("access_token");
			System.out.println("accesstoken----更新完成");
			
		}

		// 保证重启服务时，让本地accesstoken是最新的
		if (MessageUtil.ACCESS_TOKEN.isEmpty()) {
			MessageUtil.ACCESS_TOKEN = accessToken;
			System.out.println("accesstoken--------------未进行:"+MessageUtil.ACCESS_TOKEN);
		}
		return MessageUtil.ACCESS_TOKEN;

	}

	/**
	 * @TODO 从WX服务器获得AccessToken
	 * @Time 2017年11月9日 上午9:24:30
	 * @author WEQ
	 */
	private Map<String, String> httpGetAccessToken() {
		Map<String, Object> map = new HashMap<String, Object>(5);
		map.put("grant_type", "client_credential");
		map.put("appid", MessageUtil.WX_APPID);
		map.put("secret", MessageUtil.WX_APPSECRET);
		String jsonStr = null;
		// 进行get请求，调用微信接口
		try {
			jsonStr = HttpUtils.HttpClientGet(MessageUtil.URL_GET_TOKEN, "utf-8", map);
		} catch (Exception e) {

		}
		// 格式化返回结果
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> responseMap = new Gson().fromJson(jsonStr, type);
		if (responseMap.containsKey("errcode")) {
			// 证明获取access_token失败，输错错误代码
			System.out.println("获取accessToken失败" + responseMap.get("errcode"));
			return null;
		}
		return responseMap;
	}
	
	/**
	 * @TODO 访问WX服务器获得jsApiToken
	 * @Time 2017年11月9日 上午10:19:53
	 * @author WEQ
	 */
	private String httpGetJsApiToken() {
		getAccessToken();
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("access_token", MessageUtil.ACCESS_TOKEN);
		map.put("type", "jsapi");
		String jsonStr = null;
		// 进行get请求，调用微信接口
		try {
			jsonStr = HttpUtils.HttpClientGet(MessageUtil.URL_JSAPI_TOKEN, "utf-8", map);
		} catch (Exception e) {

		}
		// 格式化返回结果
		Type type = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> responseMap = new Gson().fromJson(jsonStr, type);
		if (!responseMap.containsKey("ticket")) {
			// 证明获取access_token失败，输错错误代码
			System.out.println("获取jsApiToken失败" + responseMap.get("errcode"));
			return null;
		}
		return responseMap.get("ticket");
	}
	
	/**
	 * 更新静态JsApiToken的值
	 */
	@Override
	public String getJsApiToken() {
		String name="jsapiToken";
		Map<String, Object> tokenMap = tokenMapper.get(name);
		if (tokenMap == null) {
			// 访问微信服务器，获取accesstoken，然后插入数据库
			String jsApiToken = httpGetJsApiToken();
			if (jsApiToken == null) {
				return null;
			}
			TokenInfo tokenInfo = new TokenInfo();
			tokenInfo.setName(name);
			tokenInfo.setToken(jsApiToken);
			tokenInfo.setCreateTime(DatetimeUtil.toDateTimeStr(new Date()));
			tokenInfo.setUpdateTime(DatetimeUtil.toDateTimeStr(new Date()));
			tokenMapper.add(tokenInfo);
			MessageUtil.JSAPI_TOKEN = jsApiToken;
			System.out.println("jsapitoken----------添加完成");
		}
		String jsapiToken = tokenMap.get("tokenVaule").toString();
		long tsf = (long) tokenMap.get("tsf");
		if (tsf > 6500) {// accesstoken的有效时间为7200秒
			// 时间超过有效时间，需要连接微信服务器重新获得
			String token = httpGetJsApiToken();
			if (token == null) {
				return null;
			}
			//更新数据库
			Map<String, Object> updateMap = new HashMap<>();
			updateMap.put("tokenValue", token);
			updateMap.put("updateTime", DatetimeUtil.toDateTimeStr(new Date()));
			updateMap.put("tokenName", name);
			tokenMapper.update(updateMap);
			MessageUtil.JSAPI_TOKEN =token;
			System.out.println("jsapitoken----更新完成");
		}
		
		// 保证重启服务时，让本地accesstoken是最新的
		if (MessageUtil.JSAPI_TOKEN.isEmpty()) {
			MessageUtil.JSAPI_TOKEN = jsapiToken;
			System.out.println("jsapitoken--------------未进行");
		}
		return MessageUtil.JSAPI_TOKEN;
	}

}
