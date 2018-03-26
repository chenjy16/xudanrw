package com.midea.trade;
import junit.framework.Assert;
import org.junit.Test;

import com.trade.rws.util.TimesliceFlowControl;

public class TimesliceFlowControlTest {

	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void test1s() {
		TimesliceFlowControl tsfc = new TimesliceFlowControl("流控", 1000, 20); //1秒不超过20次
		try {
			for (int i = 0; i < 20; i++) {
				tsfc.check();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Assert.fail(e.getMessage());
		}

		Assert.assertFalse(tsfc.allow());
		sleep(500);
		Assert.assertFalse(tsfc.allow());
		sleep(500);
		Assert.assertTrue(tsfc.allow());
		sleep(500);
		try {
			for (int i = 0; i < 19; i++) {
				tsfc.check();
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertFalse(tsfc.allow());
		sleep(1000);
		Assert.assertTrue(tsfc.allow());
		
	}

	
	
	
	@Test
	public void test2s() {
		TimesliceFlowControl tsfc = new TimesliceFlowControl("流控", 2000, 20); //2秒不超过20次
		try {
			for (int i = 0; i < 5; i++) {
				tsfc.check();
			}
		    sleep(500);
			for (int i = 0; i < 5; i++) {
				tsfc.check();
			}
		    sleep(500);
			for (int i = 0; i < 5; i++) {
				tsfc.check();
			}
		    sleep(500);
			for (int i = 0; i < 5; i++) {
				tsfc.check();
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	    Assert.assertFalse(tsfc.allow()); //最后一个slot放第6个请求，返回false
	    
	    sleep(1510);
		try {
			for (int i = 0; i < 14; i++) {
				tsfc.check();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		Assert.assertFalse(tsfc.allow());
	}
}

