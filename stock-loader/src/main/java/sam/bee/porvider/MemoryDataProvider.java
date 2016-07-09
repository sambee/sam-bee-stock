package sam.bee.porvider;

import org.json.JSONArray;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/9.
 */
public class MemoryDataProvider  extends BasicDataProvider {
    private static MemoryDataProvider instance;//缓存池唯一实例
    private Map<String,Object> cache ;//缓存Map

    /**
     * 得到唯一实例
     * @return
     */
    public synchronized static MemoryDataProvider getInstance(){
        if(instance == null){
            instance = new MemoryDataProvider();
        }
        return instance;
    }

    /**
     *
     */
    private MemoryDataProvider(){
        cache = new HashMap<String,Object>();
    }



    public void set(String key, String... keys) throws IOException {
        set(key.getBytes(), keys);
    };


    @Override
    public void set(byte[] bytes, String... keys)throws IOException {
        String keystr  = join(File.separator,keys);

    }

    @Override
    public  String getString(String... key) throws IOException{
        String keyStr = join(File.separator, key);
        return (String) cache.get(keyStr);
    };

    @Override
    public  byte[] getBin(String... key) throws IOException{
        String keyStr = join(File.separator, key);
        return (byte[]) cache.get(keyStr);
    };


    private  String join(String sp, String... key){
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
        cache.clear();
    };

    private void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);
        fileOrDirectory.delete();
    }

    @Override
    public boolean cleanCache(String... key){
        String keyStr =join(File.separator, key);
        cache.remove(keyStr);
        return true;

    }


    @Override
    public void set(List<Map<String, String>> values, String... keys)
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
        return cache.get(join(File.separator, key))!=null;
    }



}
