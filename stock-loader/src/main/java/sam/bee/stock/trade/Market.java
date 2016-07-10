package sam.bee.stock.trade;

import sam.bee.porvider.FileDataProvider;
import sam.bee.porvider.IDataProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static sam.bee.stock.Const.*;
import static sam.bee.stock.trade.DataUtil.getDate;

/**
 * Created by Administrator on 2016/7/9.
 */
public class Market extends Observable  {

    IDataProvider dataProvider = new FileDataProvider();
    Map<String, Map<String,String>>  codeMap = new HashMap<String, Map<String, String>>();
    String currentDate = "2015-12-31";
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Set<String> tradeDate = new HashSet<String>();
    Map<String, Map> histories = new HashMap<String, Map>();
    List<Map<String,String>> allStockInfos = new LinkedList<Map<String, String>>();
    private boolean isTrade(){
        int   week   =   calendar.get(Calendar.DAY_OF_WEEK)-1;
        final int SUNDAY =0;
        final int SATURDAY =0;

        if(week==SUNDAY || week==SATURDAY){
            return true;
        }
        return tradeDate.contains(currentDate);
    }

    public Market() throws Exception {
        List<Map<String, String>> shanghai =  dataProvider.getList(CODE, SHUANG_HAI);

        List<Map<String, String>> shengzhen =  dataProvider.getList(CODE, SHENG_ZHEN);
        for(Map<String,String> m : shanghai){
            codeMap.put(m.get(STOCK_CODE), m);
            codeMap.put(m.get(STOCK_NAME), m);
        }
        allStockInfos.addAll(allStockInfos);

        for(Map<String,String> m : shengzhen){
            codeMap.put(m.get(STOCK_CODE), m);
            codeMap.put(m.get(STOCK_NAME), m);
            allStockInfos.add(m);
        }
        final String TRADE_CODE = "601398";
        List<Map<String, String>> dl = getHistory(TRADE_CODE, dateFormat.format(new Date(System.currentTimeMillis())));
        String[] sl = getDate(dl);
        for(String s : sl){
            tradeDate.add(s);
        }



        setDate(currentDate);
    }

    public List<Map<String,String>> getStockInfos(){
        return allStockInfos;
    }

    public String getCurrentDate(){
        return currentDate;
    }

    public void addAgent(Agent agent){
        addObserver(agent);
    }

    public void setDate(String date) throws ParseException {
        this.currentDate = date;
        Date d =  dateFormat.parse(date);
        calendar.setTime(d);
    }

    public Map getStock(String code){
        return codeMap.get(code);
    }

    private int getIntDate(String date){
        return Integer.valueOf(date.replaceAll("-", ""));
    }

    public String next(){
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String date  = dateFormat.format(calendar.getTime());

        currentDate = date;
        if(isTrade()) {
            setChanged();
            notifyObservers(currentDate);
        }
        return currentDate;
    }

    public Map getData(String code, String toDate) throws Exception {
        List<Map<String, String>>  data = getHistory(code, toDate);
        if(data!=null && data.size()>0){
            Map d  =data .get(data.size()-1);
            String sdate = (String) d.get(DATE);
            if(sdate.equals(toDate)){
                return d;
            }
        }
        return null;
    }

    public Map getData(String code) throws Exception {
        return getData(code, currentDate);
    }


    public List<Map<String, String>> getHistory(String code, String toDate) throws Exception {
        String name = (String) getStock(code).get(STOCK_NAME);
        List<Map<String,String>> list = dataProvider.getList(HISTORY, code+"-"+name);
        List<Map<String,String>> ret = new ArrayList<Map<String, String>>();
        int icDate = getIntDate(toDate);
        for(Map<String,String> m : list){
            String date = m.get(DATE);
            int iDate = getIntDate(date);
            if(iDate>icDate){
                break;
            }
            ret.add(m);
        }
        return ret;
    }
    public List<Map<String, String>> getHistory(String code) throws Exception {
        return getHistory(code, currentDate);
    }


    public void order(Agent agent, String stockCode, int buyOrSell, double unit, double price){

    }
}
