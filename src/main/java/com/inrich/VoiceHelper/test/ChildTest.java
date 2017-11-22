package com.inrich.VoiceHelper.test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inrich.VoiceHelper.service.BankService;
import com.inrich.VoiceHelper.service.ComposeVoiceService;
import com.inrich.VoiceHelper.service.CreateMenuService;
import com.inrich.VoiceHelper.service.IntrodutionService;
import com.inrich.VoiceHelper.service.OperateVoiceService;
import com.inrich.VoiceHelper.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChildTest {
	@Autowired
	private IntrodutionService introdutionService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private OperateVoiceService operateVoiceService;
	@Autowired
	private ComposeVoiceService composeVoiceService;
	@Autowired
	private BankService bankService;
	@Autowired
	private CreateMenuService createMenuService;

	@Test
	public void testIndexIntrodution() {
		System.out.println(introdutionService.getIndexIntrodution());
	}
	
	@Test
	public void testNormalIntrodution() {
		System.out.println(introdutionService.getNormalIntroduction("贷前风险管理服务", 1, 2));
	}
	@Test
	public void testTextQa() throws UnsupportedEncodingException {
		String qa1="介绍下上海财安";
		String qa2="金融风险管理服务";
		String qa3="现金清分清点服务";
		System.out.println(operateVoiceService.analysisText(qa3,"main"));
	}
	@Test
	public void testToken() {
		System.out.println(tokenService.getJsApiToken());
	}
	@Test
	public void testanalyzeVoice() throws UnsupportedEncodingException {
		long sTime=System.currentTimeMillis();
		String voicePath="D:\\wxtoxf\\lR7ee1W1DqrVu1AUuH6MS7Jx0x7x4Ex4aQss2SMHwSaSxHITL89QHKd14ss6kZZ_.wav";
		String result=operateVoiceService.analysisVoice(voicePath,"mian");
		System.out.println(result);
		System.out.println("消耗时间:"+(System.currentTimeMillis()-sTime));
	}
	@Test
	public void testComposeVoice() {
		String text="由四部分组成，大堂引导服务、现金清分清点服务、定点收款服务、资料扫描录入服务";
		String path="D:\\xfpcm\\aa.pcm";
		try {
			composeVoiceService.composeVoice(text, path, Thread.currentThread());
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("-----合成结束，提前结束");
		}
		System.out.println("-----休眠结束");
	}
	
	@Test
	public void testBankIndex() {
		System.out.println(bankService.getIndexInfo());
	}
	
	@Test
	public void testBankNormalInfo() {
		System.out.println(bankService.getNormalInfo(1, 1));
	}
	 
	@Test
	public void testBankTextQa() throws UnsupportedEncodingException{
		System.out.println(bankService.doTxtQa("忘记登录密码怎么办", "bank"));
	}
	
	@Test
	public void testBankVoiceQa() throws UnsupportedEncodingException{
		String errorPath="D:\\wxtoxf\\uY3arxD06cULwgx503lir4POIRqBQNbDBhzGOp51CW7hf4bWe6vU7b2hwRxUw7Rw.wav";
		String rightPath="D:\\wxtoxf\\lCGiZCm5XKxxac3cLcFPVvIfvaPaSP95vzNbvpvRDcXGwkqj1j-hO_lxvl0Q_fsc.wav";
		//System.out.println(operateVoiceService.analysisVoice(errorPath, "main"));
		System.out.println(bankService.doVoiceQa(rightPath,"bank"));
	}
	@Test
	public void testCreatMenu() {
		createMenuService.updateMenu();
	}

}
