package scai.jedis.service;

import java.util.Map;
import java.util.Set;
/**
 * 
 * @author liujiqiao
 *
 */
public interface JedisService  {
	/**
	 * 设置缓存
	 * 
	 * @param prefix
	 *            缓存前缀（用于区分缓存，防止缓存键值重复）
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存value
	 *            
	 * @return String  ok:成功    
	 */
	public String set(String prefix, String key, String value) throws Exception  ;
	
	/**
	 * 获取指定key的缓存
	 * 
	 * @param prefix
	 * @param key
	 */
	public Object get(String prefix, String key) throws Exception ;
	
	/**
	 * 获取指定key的缓存
	 * @param key
	 */
	public Object get(String key) throws Exception ;
	/**
	 * 获取所有缓存
	 * @param Map 键
	 * @return 值
	 */
	public  Map<String,Object> getList() throws Exception ;
	/**
	 * 根据缓存前缀 查询
	 * @param Map 键
	 * @return 值
	 */
	public  Map<String,Object> getList(String prefix) throws Exception ;
	
	
	/**
	 * 删除指定的 值
	 * @param long 1 ：成功
	 * @return 值
	 */
	public  Long delete(String key) throws Exception ;
	
	/**
	 * 获取所有的key
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public Set<String> getKeys(String pattern)throws Exception;
	
	
}
