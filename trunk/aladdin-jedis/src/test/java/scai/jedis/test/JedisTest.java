package scai.jedis.test;

import org.junit.Test;

import scai.jedis.service.JedisService;
import scai.jedis.service.JedisServiceImpl;

public class JedisTest {
	
//	public JedisService JedisService=new JedisServiceImpl("127.0.0.1",6379,1024,8,50000,null,null);
	public JedisService JedisService = new JedisServiceImpl(null,6379,1024,8,50000,null,"172.16.51.175:7000,172.16.51.176:7002,172.16.51.178:7004,172.16.51.175:7001,172.16.51.176:7003,172.16.51.178:7005");
//	public JedisService JedisService = new JedisServiceImpl(null,6379,1024,8,50000,null,"10.0.11.32:7000,10.0.11.32:7001,10.0.11.33:7002,10.0.11.33:7003,10.0.11.34:7004,10.0.11.34:7005");
	
	@Test
	public void save() throws Exception{
		System.out.println(JedisService.set("fengkong_service", "test2", "test2"));;
	}
	@Test
	public void get()throws Exception{
		System.out.println(JedisService.get("fengkong_service", "test1").toString());
	}
	
	@Test
	public void getList() throws Exception{
		System.out.println(JedisService.getList("fengkong_service").toString());
	}
	
	@Test
	public void del() throws Exception{
		System.out.println(JedisService.delete("fengkong_service:bqcxRm001").toString());;
	}
}
