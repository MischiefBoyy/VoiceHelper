package com.inrich.VoiceHelper.service;

public interface ComposeVoiceService {
	
	/**
	 * 根据汉语合成语音
	 * @param sqlAnswer   将要合成的文字
	 * @param composedPath  合成的录音保存的地址
	 * @param t  合成录音线程 
	 */
	void composeVoice(String sqlAnswer,String composedPath,Thread t);

}
