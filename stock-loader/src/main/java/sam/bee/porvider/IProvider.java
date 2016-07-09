package sam.bee.porvider;

import java.util.List;
import java.util.Map;



public interface IProvider {

	/**
	 * 将值放到指定的目录下
	 * @param value
	 * @param keys  directory0,directory1,directory2....
	 * @throws Exception
	 */
	void set(String value, String... keys) throws Exception ;
	
	/**
	 * 
	 * @param value
	 * @param key
	 * @throws Exception
	 */
	void set(List<Map<String,String>> value, String... key) throws Exception;
	
	/*
	 * 
	 * 
	 */
	String getString(String... key) throws Exception;
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	List<Map<String,String>> getList(String... key) throws Exception;
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean exist(String... key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean existIgnoreTime(String... key);
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	boolean cleanCache(String... key) throws Exception;
	
}
