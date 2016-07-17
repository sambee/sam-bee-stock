package sam.bee.porvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class BasicDataProvider implements IDataProvider {
	
	/**
	 * 
	 */
	public void setList(List<Map<String, String>> values, String... keys)throws Exception{
		throw new NotYetImplementedException(this.getClass());
	};

	/**
	 * 
	 */
	public void set(String value, String... key) throws Exception{
		throw new NotYetImplementedException(this.getClass());
	}

	/*
	 * 
	 */
	public void set(byte[] bytes, String... key) throws Exception{
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	public String getString(String... key) throws Exception{
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public byte[] getBin(String... key) throws IOException{
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * 
	 * @return
	 */
	public void cleanAllCache(){
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * @throws Exception 
	 * 
	 */
	public boolean cleanCache(String... key) throws Exception{
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	public List<Map<String, String>> getList(String... key) throws Exception{
		throw new NotYetImplementedException(this.getClass());
	}

	/**
	 * 
	 */
	@Override
	public boolean exist(String... key) {
		throw new NotYetImplementedException(this.getClass());
	}
	

}
