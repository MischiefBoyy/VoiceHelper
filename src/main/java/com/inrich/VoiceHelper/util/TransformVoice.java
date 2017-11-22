package com.inrich.VoiceHelper.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TransformVoice {
	
	/**
	 * 将前台上传wav格式的录音,转换成科大讯飞要求的音频格式  修改后音频文件格式为 16k 16000 单声道  wav
	 * @param beforePath
	 * @param afterPath
	 * 
	 */
	public static void doTransformWav2Wav(String beforePath,String afterPath,String ffPath) {
		List<String> command = new ArrayList<String>();
		command.add(ffPath + "ffmpeg");
		command.add("-i");
		command.add(beforePath);
		command.add("-ar");
		command.add("16000");
		command.add("-ab");
		command.add("16k");
		command.add("-ac");
		command.add("1");
		command.add("-y");
		command.add(afterPath);

		Process videoProcess;
		try {
			videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			new PrintStream(videoProcess.getErrorStream()).start();
			new PrintStream(videoProcess.getInputStream()).start();
			videoProcess.waitFor();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * 
	* @Title: doTransformPcm2Wav  Wav/MP3  如果为wav 
	* @Description: TODO(将科大讯飞返回的PCM录音转换成wav格式语音)
	* @param @param beforePath
	* @param @param afterPath
	* @param @param toType类型为pcm_s16le  MP3则为libmp3lame
	* @return void    返回类型
	* @throws
	 */
	public static void doTransformPcm2Wav(String beforePath,String afterPath,String toType,String ffPath) {
		List<String> command = new ArrayList<String>();
		command.add(ffPath + "ffmpeg");
		command.add("-y");
		command.add("-f");
		command.add("s16le");
		command.add("-ar");
		command.add("8000");
		command.add("-ac");
		command.add("2");
		command.add("-i");
		command.add(beforePath);
		command.add("-acodec");
		command.add(toType);
		command.add(afterPath);

		Process videoProcess;
		try {
			videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
			new PrintStream(videoProcess.getInputStream()).start();
			new PrintStream(videoProcess.getInputStream()).start();
			videoProcess.waitFor();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

	}

}

class PrintStream extends Thread {
	java.io.InputStream __is = null;

	public PrintStream(java.io.InputStream is) {
		__is = is;
	}

	public void run() {
		try {
			while (this != null) {
				int _ch = __is.read();
				if (_ch != -1)
					System.out.print((char) _ch);
				else
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
