package sam.bee.cache;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class AbstractCache  implements ICache{
	
	/**
	 * 
	 */
	public void set(List<Map<String, String>> values, String... keys)throws Exception{
		throw new NotImplementedException(this.getClass());
	};

	/**
	 * 
	 */
	public void set(String value, String... key) throws Exception{
		throw new NotImplementedException(this.getClass());
	}

	/*
	 * 
	 */
	public void set(byte[] bytes, String... key) throws Exception{
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	public String getString(String... key) throws Exception{
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public byte[] getBin(String... key) throws IOException{
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * 
	 * @return
	 */
	public boolean cleanAllCache(){
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public boolean cleanCache(String... key) throws Exception{
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	public List<Map<String, String>> getList(String... key) throws Exception{
		throw new NotImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	@Override
	public boolean exist(String... key) {
		throw new NotImplementedException(this.getClass());
	}
	
	/**
	 * 
	 */
	@Override
	public boolean existIgnoreTime(String... key) {
		throw new NotImplementedException(this.getClass());
	}
	
}
