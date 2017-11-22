package com.inrich.VoiceHelper.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix="path",ignoreUnknownFields=false)
@PropertySource("classpath:config/remote.properties")
@Component
public class RemoteProperties {
	private String wx;
	private String wxtoxf;
	private String xfpcm;
	private String pcm2mp3;
	private String ffmpeg;
	public String getWx() {
		return wx;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}
	public String getWxtoxf() {
		return wxtoxf;
	}
	public void setWxtoxf(String wxtoxf) {
		this.wxtoxf = wxtoxf;
	}
	public String getXfpcm() {
		return xfpcm;
	}
	public void setXfpcm(String xfpcm) {
		this.xfpcm = xfpcm;
	}
	public String getPcm2mp3() {
		return pcm2mp3;
	}
	public void setPcm2mp3(String pcm2mp3) {
		this.pcm2mp3 = pcm2mp3;
	}
	public String getFfmpeg() {
		return ffmpeg;
	}
	public void setFfmpeg(String ffmpeg) {
		this.ffmpeg = ffmpeg;
	}
	
	
	

}
