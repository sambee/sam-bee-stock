package sam.bee.porvider;

import org.slf4j.Logger;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CSVDataProvider implements IDataProvider{
    File base = null;
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(CSVDataProvider.class);
    /**
     *
     * @param fileSpace
     */
    public CSVDataProvider(String fileSpace){
        base = new File(fileSpace);

    }
    public CSVDataProvider(){
        this(System.getProperty("user.home") + "/.stocks");
    }

    /**
     * @see IDataProvider#set(String, String...)
     */
    @Override
    public void set(String allJson, String... keys) throws IOException {
        File outFile = new File(base, join(File.separator,keys));
        ByteArrayInputStream in = new ByteArrayInputStream(allJson.getBytes());
        if(!outFile.getParentFile().exists()){
            if(!outFile.getParentFile().mkdirs()){
                throw new RuntimeException("Not create directory:"+ outFile.getParentFile().getAbsolutePath());
            }
        }
        FileOutputStream out = new FileOutputStream(outFile);
        writeAndClose(in, out);
    };

    public void writeAndClose(InputStream in, OutputStream out) throws IOException{
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

    @Override
    public  String getString(String... key) throws IOException{
        throw new RuntimeException("Not yet implement");
    };


   private String join(String sp, String... key){
        if(key.length==0)
            return "";
        StringBuffer sb = new StringBuffer();
        for( int i=0;i<key.length-1;i++){
            sb.append(key[i]).append(sp);
        }
        return sb.append(key[key.length-1]).toString();
    }


    @Override
    public boolean cleanCache(String... key){
        throw new RuntimeException("Not yet implement");

    }


    @Override
    public void setList(List<Map<String, String>> values, String... keys)
            throws Exception {
        if(values==null || values.size()==0){
            return;
        }
        Map<String, String> map = values.get(0);
        StringBuilder header = new StringBuilder();
        for(String v : map.keySet()){
            header.append(v + ",");
        }
        header.append("MEMO\n");

        File outFile = new File(base, join(File.separator,keys));
        OutputStreamWriter w = null ;

        try {
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
                logger.info("build the directory:" + outFile.getParentFile().getCanonicalPath());
            }
            w = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
            w.write(header.toString());

            for (Map<String, String> v : values) {
                String val = "";
                for (String k : v.keySet()) {
                    val += v.get(k) + ",";
                }
                w.write(val + "\n");
                w.flush();
            }
        }
        finally {
            if(w!=null){
                w.close();
            }
        }
        return;

    }

    @Override
    public List<Map<String, String>> getList(String... keys) throws Exception {

        List<Map<String,String>> l = new LinkedList<>();
        File file = new File(base, join(File.separator,keys));
        InputStreamReader in = null;

        try {
            if(!file.exists()){
                System.err.println("Not found the file." + file.getCanonicalPath());
                return l;
            }
            in=new InputStreamReader(new FileInputStream(file), "UTF-8");

            BufferedReader br = new BufferedReader(in);
            String line = null;
            int i = 0;
            String[] header = new String[0];
            String[] values;
            Map m;
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    header = line.split(",");
                } else {
                    values = line.split(",");
                    m = new LinkedHashMap();
                    for (int no = 0; no < values.length; no++) {
                        m.put(header[no], values[no]);
                    }
                    l.add(m);
                }
                i++;
            }
        }
        finally {
            if(in!=null){
                in.close();
            }
        }
        return l;

    }

    @Override
    public List<Map<String, String>> getList(int day, String... keys) throws Exception {

        List<Map<String,String>> l = new LinkedList<>();
        File file = new File(base, join(File.separator,keys));
        InputStreamReader in = null;

        try {
            if(!file.exists()){
                logger.info("Not found the file." + file.getCanonicalPath());
                return l;
            }
            in=new InputStreamReader(new FileInputStream(file), "UTF-8");

            BufferedReader br = new BufferedReader(in);
            String line = null;
            int i = 0;
            String[] header = new String[0];
            String[] values;
            Map m;
            while ((line = br.readLine()) != null && i<day) {
                if (i == 0) {
                    header = line.split(",");
                } else {
                    values = line.split(",");
                    m = new LinkedHashMap();
                    for (int no = 0; no < values.length; no++) {
                        m.put(header[no], values[no]);
                    }
                    l.add(m);
                }
                i++;
            }
        }
        finally {
            if(in!=null){
                in.close();
            }
        }
        return l;
    }


    @Override
    public boolean exist(String... keys) {
       return new File(base, join(File.separator,keys)).exists();
    }


}
