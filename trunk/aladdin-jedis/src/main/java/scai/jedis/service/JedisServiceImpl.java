package scai.jedis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import scai.jedis.config.JedisConfig;
import scai.jedis.util.SerializeUtil;

/**
 * 
 * @author liujiqiao
 *
 */
public class JedisServiceImpl implements JedisService {

	protected JedisPool jedisPool;
	protected JedisCluster jedisCluster;  //modified by ray
	protected static int redisType;  // 0--单机  1--集群

	protected static final String KEY_SPLIT = ":"; // 用于隔开缓存前缀与缓存键值
	
	public JedisServiceImpl(String ip, int port, int maxTotal, int maxIdle,
			long maxWaitMillis,String auth,String nodes) {
		if(nodes == null){  // 单机版
			this.jedisPool = JedisConfig.newJedisPool(ip, port, maxTotal, maxIdle, maxWaitMillis, auth);
			redisType = 0;
		}else{  // 集群版
			List<Map<String,Object>> clusterNodes = new ArrayList<Map<String,Object>>();
			String[] nodeArray = nodes.split(",");
			for(String node:nodeArray){
				String[] clusterNode = node.split(":");
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("ip", clusterNode[0].trim());
				map.put("port", clusterNode[1].trim());
				clusterNodes.add(map);
			}
			this.jedisCluster = JedisConfig.newJedisCluster(clusterNodes, maxTotal, maxIdle, maxWaitMillis);
			redisType = 1;
		}
	}

	@Override
	public String set(String prefix, String key, String value) throws Exception {
		String result = null;
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				result = jedis.set((prefix+KEY_SPLIT+key).getBytes(), SerializeUtil.serialize(value));
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try {				
				result = jedisCluster.set((prefix+KEY_SPLIT+key).getBytes(), SerializeUtil.serialize(value));
			} catch (JedisException e){
				throw e;
			}
		}	
		return result;
	}
	
	@Override
	public Object get(String prefix, String key) throws Exception {
		Object value = null;
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				if (jedis.exists(prefix+KEY_SPLIT+key)) {
				    byte[] data = jedis.get((prefix+KEY_SPLIT+key).getBytes());
					value = SerializeUtil.unserialize(data);
				}
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try{
				if(jedisCluster.exists(prefix+KEY_SPLIT+key)){
					byte[] data = jedisCluster.get((prefix+KEY_SPLIT+key).getBytes());
					value = SerializeUtil.unserialize(data);
				}
			} catch (JedisException e) {
				throw e;
			} 
		}
		return value;
	}
	
	@Override
	public Object get(String key) throws Exception {
		Object value = null;
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				if (jedis.exists(key)) {
				    byte[] data = jedis.get(key.getBytes());
					value = SerializeUtil.unserialize(data);
				}
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try{
				if(jedisCluster.exists(key)){
					byte[] data = jedisCluster.get(key.getBytes());
					value = SerializeUtil.unserialize(data);
				}
			} catch (JedisException e) {
				throw e;
			} 
		}
		return value;
	}
	
	@Override
	public Map<String,Object> getList(String prefix) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				Set<String> set = jedis.keys(prefix+KEY_SPLIT+"*");
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					byte[] data=jedis.get(key.getBytes());
					result.put(key, SerializeUtil.unserialize(data));
				}
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try {
				TreeSet<String> set = keys(prefix+KEY_SPLIT+"*");
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					byte[] data = jedisCluster.get(key.getBytes());
					result.put(key, SerializeUtil.unserialize(data));
				}
			} catch(JedisException e) {
				throw e;
			} 
		}
		return result;
	}
	
	@Override
	public Map<String,Object> getList() throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				Set<String> set = jedis.keys("*");
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					byte[] data = jedis.get(key.getBytes());
					result.put(key, SerializeUtil.unserialize(data));
				}				
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		} else {
			try {
				TreeSet<String> set = keys("*");
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					byte[] data = jedisCluster.get(key.getBytes());
					result.put(key, SerializeUtil.unserialize(data));
				}
			} catch (JedisException e) {
				throw e;
			} 
		}
		return result;
	}
	
	@Override
	public Long delete(String key) throws Exception{
		Long result = null;
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				if(jedis.exists(key)){					
					result = jedis.del(key.getBytes());
				}
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try{
				if(jedisCluster.exists(key)){					
					result = jedisCluster.del(key.getBytes());
				}
			} catch (JedisException e){
				throw e;
			} 
		}
		return result;
	}
	
	/**
	 * 获取所有的Key
	 * @param pattern 前缀
	 * @return
	 */
	@Override
	public Set<String> getKeys(String pattern){ 
		Set<String> set = null;
		if(redisType == 0){
			Jedis jedis = null;
			try {
				jedis = getResource();
				set = jedis.keys(pattern+"*");
			} catch (JedisException e) {
				throw e;
			} finally {
				returnBrokenResource(jedis);
			}
		}else{
			try {
				set = keys(pattern+"*");
			} catch (JedisException e) {
				throw e;
			} 
		}
		return set;
	}
	
	/**
	 * 封装集群版获取Keys
	 * @param pattern
	 * @return
	 */
	public TreeSet<String> keys(String pattern){   
        TreeSet<String> keys = new TreeSet<>();  
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            try {  
                keys.addAll(connection.keys(pattern));  
            } catch(Exception e){  
                e.printStackTrace(); 
            } finally{   
                connection.close();  // 用完一定要close这个链接！！！  
            }  
        }  
        return keys;
    }


	/**
	 * 获取资源
	 * 
	 * @return
	 * @throws JedisException
	 */
	public Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisException e) {
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}

	/**
	 * 归还资源
	 * 
	 * @param jedis
	 * @param isBroken
	 */
	public void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}

}
