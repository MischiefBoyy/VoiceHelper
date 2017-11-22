package com.inrich.VoiceHelper.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inrich.VoiceHelper.model.WeixinMedia;

public class DownLoadMedia {
	
	
	public static void main(String[] args) {
		String accessToken="NXZFaVc6oADPJVhAa4ZoFSp4gzD1NON19CVj9A_dcm0aYFEKKN2euOJ2xGUvqp3c6_GQJuVv6YVF63QSPDOEGd3gAWBtqQXiAiFFXHSkyh0PIViAJAYRE";
		String type="voice";
		String mediaFileUrl="http://localhost:80/testBaidu/music/c.mp3";
		//uploadMedia(accessToken, type, mediaFileUrl);
		
		
		String mediaId="FqgXRYBrXIGTYeNNfZsM10-SDBfmTvjpUp4DnpIZt-B4h9Gp-a72i2ylNwykUlYC";
		String savePath=downloadMedia(accessToken, mediaId, "D:\\wx");
		System.out.println("下载成功之后保存在本地的地址为："+savePath);

	}
	
	
	
	
	/**
	 * 上传媒体文件
	 * @param accessToken 接口访问凭证
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file)
	 * @param media form-data中媒体文件标识，有filename、filelength、content-type等信息
	 * @param mediaFileUrl 媒体文件的url
	 * 上传的媒体文件限制
     * 图片（image）:1MB，支持JPG格式
     * 语音（voice）：2MB，播放长度不超过60s，支持AMR格式
     * 视频（video）：10MB，支持MP4格式
     * 普通文件（file）：10MB
	 * */
	public static WeixinMedia uploadMedia(String accessToken, String type, String mediaFileUrl) {
		WeixinMedia weixinMedia = null;
		// 拼装请求地址
		String uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		// 定义数据分隔符
		String boundary = "------------7da2e536604c8";
		try {
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");

			// 从请求头中获取内容类型
			String contentType = meidaConn.getHeaderField("Content-Type");
			System.out.println("contentType:"+contentType);
			
			// 根据内容类型判断文件扩展名
			String fileExt = getFileEndWitsh(contentType);
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// 将媒体文件写到输出流（往微信服务器写数据）
				outputStream.write(buf, 0, size);
			}
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();

			// 使用JSON-lib解析返回结果
			//JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			JsonObject jsonObject=new JsonParser().parse(buffer.toString()).getAsJsonObject();
			// 测试打印结果
			System.out.println("打印测试结果"+jsonObject);
			weixinMedia = new WeixinMedia();
			weixinMedia.setType(jsonObject.get("type").getAsString());
			// type等于 缩略图（thumb） 时的返回结果和其它类型不一样
			if ("thumb".equals(type))
				weixinMedia.setMediaId(jsonObject.get("thumb_media_id").getAsString());
			else
				weixinMedia.setMediaId(jsonObject.get("media_id").getAsString());
			    weixinMedia.setCreatedAt(jsonObject.get("created_at").getAsInt());
		} catch (Exception e) {
			weixinMedia = null;
			String error = String.format("上传媒体文件失败：%s", e);
			System.out.println(error);
		}
		return weixinMedia;
	}
	
	
	/**
		 * 根据内容类型判断文件扩展名
		 * 
		 * @param contentType
		 *            内容类型
		 * @return
		 */
		public static String getFileEndWitsh(String contentType) {
			String fileEndWitsh = "";
			if ("image/jpeg".equals(contentType))
				fileEndWitsh = ".jpg";
			else if ("audio/mpeg".equals(contentType))
				fileEndWitsh = ".mp3";
			else if ("audio/amr".equals(contentType))
				fileEndWitsh = ".amr";
			else if ("video/mp4".equals(contentType))
				fileEndWitsh = ".mp4";
			else if ("video/mpeg4".equals(contentType))
				fileEndWitsh = ".mp4";
			return fileEndWitsh;
		}
		
		
		/**
		 * 获取媒体文件
		 * @param accessToken 接口访问凭证
		 * @param media_id 媒体文件id
		 * @param savePath 文件在服务器上的存储路径
		 * */
		public static String downloadMedia(String accessToken, String mediaId, String savePath) {
			String filePath = null;
			// 拼接请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
			System.out.println(requestUrl);
			try {
				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setRequestMethod("GET");

				if (!savePath.endsWith("/")) {
					savePath += File.separator;
				}
				// 根据内容类型获取扩展名
				String fileExt = getFileEndWitsh(conn.getHeaderField("Content-Type"));
				// 将mediaId作为文件名
				filePath = savePath + mediaId + fileExt;

				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				FileOutputStream fos = new FileOutputStream(new File(filePath));
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1)
					fos.write(buf, 0, size);
				fos.close();
				bis.close();

				conn.disconnect();
				String info = String.format("下载媒体文件成功，filePath=" + filePath);
				System.out.println(info);
			} catch (Exception e) {
				filePath = null;
				String error = String.format("下载媒体文件失败：%s", e);
				System.out.println(error);
			}
			return filePath;
		}

}
