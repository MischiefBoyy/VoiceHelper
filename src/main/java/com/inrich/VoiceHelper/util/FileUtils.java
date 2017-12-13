package com.inrich.VoiceHelper.util;

import java.io.File;

public class FileUtils {
	
	/**
	 * 文件夹不存在话 创建文件夹
	 * @TODO TODO
	 * @Time 2017年12月13日 下午1:55:33
	 * @author WEQ
	 * @return void
	 */
	public static void createFile(String path) {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
	}

}
