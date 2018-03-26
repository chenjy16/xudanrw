package com.midea.trade;


import javax.annotation.Resource;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.midea.trade.rws.service.MShopService;

public class MybatisTest extends BaseTest{
	
	
	
	@Resource(name="mShopService")
	MShopService mShopService;
	

	
	private static Logger logger = LoggerFactory.getLogger(MybatisTest.class);
	
	
	/**
	 * @throws InterruptedException 
	 */
	@Test
	public void dataTest() throws InterruptedException{
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-shard-dao.xml");
		
		
		/*ExecutorService threadPool = Executors.newFixedThreadPool(20);
		final CountDownLatch endP=new CountDownLatch(20);
    	for(int i=0;i<20;i++){
    		threadPool.execute(new Runnable() {
				public void run() {
					try {
						//mShopService.insert();
						mShopService.select();
						//mShopService.update();
					} catch (Exception e) {
						e.printStackTrace();
					}finally{  
                        endP.countDown();  
                    } 
				}
			});
    	}
    	endP.await();
  		threadPool.shutdown();*/
		//mShopService.select();
		//mShopService.update();
		try {
			mShopService.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sqlSession.selectList("select shop_id, platform_id, shop_name, owner, owner_mobile, email, status, guarantee_store_id, logistics_com_code, shop_type, return_store_id, outer_shop_id, shop_cate, shop_join_time, business_units, operation_attri from m_shop_test_rw ", "1");
	}
	
	/**
	 * @throws InterruptedException 
	 */
	@Test
	public void timeTest() throws InterruptedException{
		ApplicationContext context = new ClassPathXmlApplicationContext("/testContext.xml");
		long start=System.currentTimeMillis();
		mShopService.update();
		logger.info((System.currentTimeMillis()-start)+"======================================");
	}
	
	/**
	 * @throws InterruptedException 
	 */
	@Test
	public void time2Test() throws InterruptedException{
		ApplicationContext context = new ClassPathXmlApplicationContext("/testContext.xml");
		long start=System.currentTimeMillis();
		try {
			mShopService.insert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info((System.currentTimeMillis()-start)+"======================================");
	}
	
}
