package sam.bee.stock.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.porvider.JsonHelper;
import sam.bee.stock.loader.util.FreeMarkerUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/7/19.
 */
public class QQHistoryLoader extends BaseLoader implements ILoader {

    protected static final Logger logger = LoggerFactory.getLogger(QQHistoryLoader.class);
    String code;

    String mCode;
    //public  String URL = "http://web.ifzq.gtimg.cn/appstock/app/fqkline/get?_var=kline_dayhfq&param=sh600103,day,1998-01-01,1998-12-31,320,hfq&r=0.444157593883574";
    public String URL = "http://web.ifzq.gtimg.cn/appstock/app/fqkline/get?_var=kline_dayqfq&param=${code},day,,,640,qfq&r=0.48481834312513383";
    public String CODE_TEMP = "<#if code?starts_with(\"0\")  || code?starts_with(\"3\")  >sz${code}<#else>sh${code}</#if>";

    protected String  get(String code) throws Exception {
        mCode = FreeMarkerUtils.convert(CODE_TEMP, "code",code);
        String url = FreeMarkerUtils.convert(URL, "code", mCode);
        return getResponse(url);
    }

    public List<Map<String,String>> parse(String dataStr) {
        String json = dataStr;
        json = json.substring(json.indexOf("{"));
        Map m = JsonHelper.toMap(json);
        Map data= (Map) m.get("data");
        Map l = (Map) data.get(mCode);
        List ll = (List) l.get("qfqday");
        Vector<Map<String,String>> v = new Vector<>();
        for(int i=0;i<ll.size();i++){
            List dat = (List) ll.get(i);
            v.add(m((String)dat.get(0), (String)dat.get(1), (String)dat.get(2), (String)dat.get(3), (String)dat.get(4), (String)dat.get(5)));
        }
        Collections.sort(v, new Comparator<Map<String, String>>() {

            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String code1 = o1.get("DATE");
                String code2 = o2.get("DATE");
                return code1.compareTo(code2);
            }

        });
        return v;
    }



    private Map m(String date, String open, String close, String high, String low, String vol ){
        Map m  = new LinkedHashMap();
        m.put("DATE",date);
        m.put("OPEN",open);
        m.put("LOW",low);
        m.put("HIGH",high);
        m.put("CLOSE",close);
        m.put("VOLUME", vol);
        return m;
    }

    public QQHistoryLoader(String code){
        this.code = code;
        throw new RuntimeException("wrong data for web.ifzq.gtimg.cn");
    }

    @Override
    public List<Map<String,String>>  execute() throws Exception {
        String dataStr = get(code);
        logger.info(dataStr);
        return parse(dataStr);

    }

    public static void main(String[] args) throws Exception {
        List l = (List) new QQHistoryLoader("600103").execute();
        System.out.println(l);

    }
}
