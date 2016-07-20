package sam.bee.porvider;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016/7/20.
 */
public class CSVDataProvider implements IDataProvider{
    File base = null;

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
        throw new RuntimeException("Not yet implement");
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
        Map<String, String> map = values.get(0);
        StringBuilder header = new StringBuilder();
        for(String v : map.keySet()){
            header.append(v + ",");
        }
        header.append("MEMO\n");

        File outFile = new File(base, join(File.separator,keys));
        OutputStreamWriter w = null ;

        try {
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
    public boolean exist(String... key) {
        throw new RuntimeException("Not yet implement");
    }


}
