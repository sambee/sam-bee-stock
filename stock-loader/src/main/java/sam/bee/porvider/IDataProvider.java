package sam.bee.porvider;

import java.util.List;
import java.util.Map;



public interface IDataProvider {

	/**
	 * 将值放到指定的目录下
	 * @param allJson
	 * @param keys  directory0,directory1,directory2....
	 * @throws Exception
	 */
	void set(String allJson, String... keys) throws Exception ;
	
	/**
	 * 
	 * @param data
	 * @param key
	 * @throws Exception
	 */
	void setList(List<Map<String,String>> data, String... key) throws Exception;
	
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
	 * @throws Exception
	 */
	boolean cleanCache(String... key) throws Exception;
	
}
