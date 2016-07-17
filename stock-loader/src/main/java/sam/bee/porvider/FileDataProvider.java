package sam.bee.porvider;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;




public class FileDataProvider extends BasicDataProvider {

	File base = null;
	
	/**
	 * 
	 * @param fileSpace
	 */
	public FileDataProvider(String fileSpace){
		base = new File(fileSpace);

	}
	public FileDataProvider(){
		this(System.getProperty("user.home") + "/.stocks");
	}


	public void set(String key, String... keys) throws IOException  {
		set(key.getBytes("UTF-8"), keys);
	};

	
	@Override
	public void set(byte[] bytes, String... keys)throws IOException {
		File outFile = new File(base, join(File.separator,keys));
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		if(!outFile.getParentFile().exists()){
			if(!outFile.getParentFile().mkdirs()){
				throw new RuntimeException("Not create directory:"+ outFile.getParentFile().getAbsolutePath());
			}
		}
		FileOutputStream out = new FileOutputStream(outFile);
		writeAndClose(in, out);	
	}
	
	@Override
	public  String getString(String... key) throws IOException{
		File f = new File(base, join(File.separator, key));		
		if(!f.exists()){			
			return null;
		}
		return readFile(f,"UTF-8");
	};
	
	@Override
	public  byte[] getBin(String... key) throws IOException{
		File f = new File(base, join(File.separator, key));
		if(!f.exists()){
			return null;
		}
		return readFile(f);
	};
	

	String join(String sp, String... key){
		if(key.length==0)
			return "";
		StringBuffer sb = new StringBuffer();
		for( int i=0;i<key.length-1;i++){
			sb.append(key[i]).append(sp);
		}
		return sb.append(key[key.length-1]).toString();
	}
	
	@Override
	public void cleanAllCache(){
//		deleteRecursive(base);
//		return true;
		throw new RuntimeException("Not supported yet.");
	};
	
	private void deleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles())
	        	deleteRecursive(child);
	    fileOrDirectory.delete();	 
	}
	
	@Override
	public boolean cleanCache(String... key){
		File f = new File(base, join(File.separator, key));		
		if(f.exists()){
			if(f.isDirectory()){
				deleteRecursive(f);
			}
			else{
				return f.delete();
			}
			
		}
		return true;		
		
	}
	
	public File getBasicPath(){
		return base;
	}

	@Override
	public void setList(List<Map<String, String>> values, String... keys)
			throws Exception {
		set(JsonHelper.toJSON(values).toString(), keys);
	}

	@Override
	public List<Map<String, String>> getList(String... key) throws Exception {
		String json = getString(key);
		if(json==null){
			return null;
		}
		return JsonHelper.toList( new JSONArray(json));
	}


	@SuppressWarnings("resource")
	public static String readFile(File file, String charset) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		StringBuffer sb = new StringBuffer();
		String line;
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		br.close();
		return sb.toString();
	};
	
	public static byte[] readFile(File file) throws IOException{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		writeAndClose(new FileInputStream(file), bytes );
		return bytes.toByteArray();
	};
	
	public static void writeAndClose(InputStream in, OutputStream out) throws IOException{
		try{
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len != -1) {
		    out.write(buffer, 0, len);
		    len = in.read(buffer);
		}
		}
		finally{
			in.close();
			out.close();	
		}

	};
	
	public static void DeleteDirs(File dir){
		dir.deleteOnExit();
	}


	@Override
	public boolean exist(String... key) {		
		File file = new File(base, join(File.separator, key));

		return file.exists();
	}


	public String getBasicCacheFilePath(){
		return base.getAbsolutePath();
	}

}
