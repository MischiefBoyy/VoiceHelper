package com.inrich.VoiceHelper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpUtils {

	
	@SuppressWarnings("resource")
	public static String HttpClientPost(String url, String charset, Map<String, Object> entity,
			Map<String, Object> headers) throws Exception {
		/**
		 * 返回结果
		 */
		String result = "";
		/**
		 * 请求状态码
		 */
		int status = 200;

		/**
		 * 请求类型
		 */
		HttpPost httpPost;
		/**
		 * 请求返回结果
		 */
		HttpResponse response = null;

		HttpClient httpClient = null;

		httpPost = new HttpPost(url);

		// 设置body
		if (entity != null) {
			List<NameValuePair> params = new ArrayList<NameValuePair>(16);
			entity.forEach((k, v) -> {
				params.add(new BasicNameValuePair(k, v.toString()));
			});
			httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		}

		if (headers != null) {
			headers.forEach((k, v) -> {
				httpPost.addHeader(k, v.toString());
			});
		}

		// 获取当前客户端对象
		httpClient = new DefaultHttpClient();
		// 通过请求对象获取响应对象
		try {
			response = httpClient.execute(httpPost);
			// 获取请求返回的结果集
			// 判断请求成功
			if (response.getStatusLine().getStatusCode() == status) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(result);
			}
		} catch (Exception ex) {
			throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage());
		} finally {
			httpPost.releaseConnection();
		}

		return result;
	}

	/**
	 * 
	 * @Title: HttpClientGet @Description: TODO(进行Get请求) @param @param url
	 *         访问地址结尾不带？ @param @param charset 编码及 @param @param entity
	 *         参数 @param @return @param @throws Exception 设定文件 @return String
	 *         返回类型 @throws
	 */
	@SuppressWarnings("resource")
	public static String HttpClientGet(String url, String charset, Map<String, Object> entity) throws Exception {
		/**
		 * 返回结果
		 */
		String result = "";
		/**
		 * 请求状态码
		 */
		int status = 200;

		/**
		 * 请求类型
		 */
		HttpGet httpGet = null;
		/**
		 * 请求返回结果
		 */
		HttpResponse response = null;

		HttpClient httpClient = null;

		String paramStr = "";

		try {
			// 设置请求参数
			if (entity != null) {
				List<NameValuePair> params = new ArrayList<NameValuePair>(16);
				entity.forEach((k, v) -> {
					params.add(new BasicNameValuePair(k, v.toString()));
				});
				paramStr = "?" + EntityUtils.toString(new UrlEncodedFormEntity(params), Consts.UTF_8);
			}
			System.out.println("----url:"+url + paramStr);
			// 设置请求地址
			httpGet = new HttpGet(url + paramStr);
			// 获取当前客户端对象
			httpClient = new DefaultHttpClient();
			// 通过请求对象获取响应对象
			response = httpClient.execute(httpGet);

			// 获取请求返回的结果集
			// 判断请求成功
			if (response.getStatusLine().getStatusCode() == status) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(result);
			}
		} catch (Exception ex) {
			throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage());
		} finally {
			httpGet.releaseConnection();
		}
		return result;
	}

	
	@SuppressWarnings("resource")
	public static String HttpClientPost(String url, String charset, String jsonStr, Map<String, Object> headers)
			throws Exception {
		/**
		 * 返回结果
		 */
		String result = "";
		/**
		 * 请求状态码
		 */
		int status = 200;

		/**
		 * 请求类型
		 */
		HttpPost httpPost;
		/**
		 * 请求返回结果
		 */
		HttpResponse response = null;

		HttpClient httpClient = null;

		httpPost = new HttpPost(url);

		// 设置body
		if (jsonStr != null) {
			httpPost.setEntity(new StringEntity(jsonStr, Consts.UTF_8));
		}

		if (headers != null) {
			headers.forEach((k, v) -> {
				httpPost.addHeader(k, v.toString());
			});
		}

		// 获取当前客户端对象
		httpClient = new DefaultHttpClient();
		// 通过请求对象获取响应对象
		try {
			response = httpClient.execute(httpPost);
			// 获取请求返回的结果集
			// 判断请求成功
			if (response.getStatusLine().getStatusCode() == status) {
				result = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(result);
			}
		} catch (Exception ex) {
			throw new Exception("通过httpClient进行post提交异常：" + ex.getMessage());
		} finally {
			httpPost.releaseConnection();
		}

		return result;
	}
	
	
	

}
