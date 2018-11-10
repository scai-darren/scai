package scai.jedis.config;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author liujiqiao
 * 
 */
public class JedisConfig {

	private static volatile JedisPool JedisPool = null;
	private static volatile JedisCluster jedisCluster = null;

	public static JedisPool newJedisPool(String ip, int port, int maxTotal, int maxIdle, 
			long maxWaitMillis, String auth) {
		if (JedisPool == null) {
			synchronized (JedisConfig.class) {
				if (JedisPool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					// 最大连接数, 默认8个
					config.setMaxTotal(maxTotal);
					// 最大空闲连接数, 默认8个
					config.setMaxIdle(maxIdle);
					// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常,
					config.setMaxWaitMillis(maxWaitMillis);
					// //对拿到的connection进行validateObject校验
					config.setTestOnBorrow(true);
					// 在进行returnObject对返回的connection进行validateObject校验
					config.setTestOnReturn(true);
					// 定时对线程池中空闲的链接进行validateObject校验
					config.setTestWhileIdle(true);
					// JedisPool = new JedisPool(config, ip, port);
					JedisPool = new JedisPool(config, ip, port, 5000, auth);
				}
			}
		}
		return JedisPool;
	}
	
	/**
	 * 连接Redis集群
	 * @param nodes
	 * @param maxTotal
	 * @param maxIdle
	 * @param maxWaitMillis
	 * @return
	 */
	public static JedisCluster newJedisCluster(List<Map<String,Object>> nodes,int maxTotal, 
			int maxIdle, long maxWaitMillis) {
		if (jedisCluster == null) {
			synchronized (JedisConfig.class) {
				if (jedisCluster == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(maxTotal);
					config.setMaxIdle(maxIdle);
					config.setMaxWaitMillis(maxWaitMillis);
					config.setTestOnBorrow(true);
					config.setTestOnReturn(true);
					config.setTestWhileIdle(true);
					Set<HostAndPort> nodeSet = new LinkedHashSet<HostAndPort>();
					for(Map<String,Object> node:nodes){
						String ip = node.get("ip").toString();
						int port = Integer.parseInt(node.get("port").toString());
						nodeSet.add(new HostAndPort(ip,port));
					}
					jedisCluster = new JedisCluster(nodeSet, config);
				}
			}
		}
		return jedisCluster;
	}
	
}
