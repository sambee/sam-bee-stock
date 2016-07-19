package sam.bee.stock.loader.impl;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.porvider.JsonHelper;
import sam.bee.stock.loader.util.FreeMarkerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 2016/7/19.
 */
public class QQHistoryQuery extends BaseQuery implements ICommand {

    protected static final Logger logger = LoggerFactory.getLogger(QQHistoryQuery.class);
    String code;
    QQHistoryLoader loader;
    QQHistoryDataAdapter adapter;
    static class QQHistoryLoader extends BaseLoader {
        String mcode;
        //public  String URL = "http://web.ifzq.gtimg.cn/appstock/app/fqkline/get?_var=kline_dayhfq&param=sh600103,day,1998-01-01,1998-12-31,320,hfq&r=0.444157593883574";
        public String URL = "http://web.ifzq.gtimg.cn/appstock/app/fqkline/get?_var=kline_dayqfq&param=${code},day,,,640,qfq&r=0.48481834312513383";
        public String CODE_TEMP = "<#if code?starts_with(\"0\")  || code?starts_with(\"3\")  >sz${code}<#else>sh${code}</#if>";
        @Override
        protected List<String> get(Object... params) throws Exception {
            mcode = FreeMarkerUtils.convert(CODE_TEMP, "code", (String)params[0]);
            String url = FreeMarkerUtils.convert(URL, "code", mcode);
            logger.info(url);
            return getResponse(url);
        }
    }

    static class QQHistoryDataAdapter implements IDataAdapter {

        QQHistoryLoader loader;
        public  QQHistoryDataAdapter( QQHistoryLoader loader){
                this.loader = loader;
         }
        @Override
        public Object parse(List<?> list) {
            String json = (String) list.get(0);
            json = json.substring(json.indexOf("{"));
            Map m = JsonHelper.toMap(json);
            Map data= (Map) m.get("data");
            Map l = (Map) data.get(loader.mcode);
            List ll = (List) l.get("qfqday");
            List<Map<String,String>> d = new Vector<>();
            for(int i=0;i<ll.size();i++){
                List dat = (List) ll.get(i);
                d.add(m((String)dat.get(0), (String)dat.get(1), (String)dat.get(2), (String)dat.get(3), (String)dat.get(4), (String)dat.get(5)));
            }
            return d;
        }



        private Map m(String date, String open, String close, String high, String low, String vol ){
            Map m  = new HashMap();
            m.put("DATE",date);
            m.put("OPEN",open);
            m.put("LOW",low);
            m.put("HIGH",high);
            m.put("CLOSE",close);
            return m;
        }
    }
    public QQHistoryQuery(String code){
        this.code = code;
        loader = new QQHistoryLoader();
        adapter =new QQHistoryDataAdapter(loader);
    }

    @Override
    public Object execute() throws Exception {
        List<String> list = loader.get(code);
        return adapter.parse(list);
    }

    public static void main(String[] args) throws Exception {
        List l = (List) new QQHistoryQuery("600103").execute();
        System.out.println(l);

    }
}
