package sam.bee.stock.trade;

import org.slf4j.Logger;
import sam.bee.porvider.CSVDataProvider;
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

    Logger logger = org.slf4j.LoggerFactory.getLogger(Market.class);

    IDataProvider dataProvider;
    public Map<String, Map<String,String>> codeOrNameMap = new HashMap<String, Map<String, String>>();
    String currentDate;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //交易日
    public Set<String> tradeDate = new HashSet<>();
    public Set<String> codes = new HashSet<String>();
    public  Map<String, List<Map<String, String>>> histories = new HashMap<String, List<Map<String, String>>>();
    public static final String TRADE_DATE_CODE = "601398";
    List<Map<String,String>> allStockInfos = new LinkedList<Map<String, String>>();



    public void init() throws Exception {

        currentDate = dateFormat.format(new Date());
        allStockInfos =  getDataProvider().getList(CODE, ALL_STOCK_INFO);
        if(allStockInfos==null || allStockInfos.size()==0){
            logger.error("Not found any history data, please load them first.");
            return;
        }
        for(Map<String,String> m : allStockInfos){
            codeOrNameMap.put(m.get(STOCK_CODE), m);
            codeOrNameMap.put(m.get(STOCK_NAME), m);
            codes.add(m.get(STOCK_CODE));
        }

        List<Map<String, String>> dl = getHistory(TRADE_DATE_CODE, currentDate);
        String[] sl = getDate(dl);
        for(String s : sl){
            tradeDate.add(s);
        }
        setCurrentDate(currentDate);
    }
    public Market(IDataProvider dataProvider) throws Exception {
        this.dataProvider = dataProvider;
        init();
    }

    public Market() throws Exception {
        this.dataProvider = new CSVDataProvider();
        init();
    }

    public IDataProvider getDataProvider() {
        if(dataProvider==null){
            throw new NullPointerException("data provider is null");
        }
        return dataProvider;
    }

    public void setDataProvider(IDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public List<Map<String,String>> getStockInfos(){
        return allStockInfos;
    }

    public void setStockInfos(List<Map<String,String>> allStockInfos){
        this.allStockInfos = allStockInfos;
    }

    public String getCurrentDate(){
        return currentDate;
    }

    public void setCurrentDate(String date) throws ParseException {
        this.currentDate = date;
        Date d =  dateFormat.parse(date);
        calendar.setTime(d);
    }

    public List<Map<String,String>> getCurrentData() throws Exception {

        List<Map<String,String>>  list  =  getDataProvider().getList(INDEX,currentDate );
        if(list!=null && list.size()>0){
            return list;
        }
        list = new LinkedList<Map<String,String>>();
        Map m;
        Map mInfo;
        for(String code : codes){
            m = getCurrentData(code);
            logger.debug(code + "=" + m);
            if(m!=null) {
                mInfo = codeOrNameMap.get(code);
                m.put(STOCK_CODE, code);
                m.put(STOCK_NAME, mInfo.get(STOCK_NAME));
                list.add(m);
            }
        }
        getDataProvider().setList(list, INDEX,currentDate );
        return list;
    }

    public void addAgent(Agent agent){
        addObserver(agent);
    }


    public Map getStockInfo(String code){
        return codeOrNameMap.get(code);
    }

    private int getIntDate(String date){
        return Integer.valueOf(date.replaceAll("-", ""));
    }

    private boolean isTradeDate(){
        return tradeDate.contains(currentDate);
    }

    public String next(){
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String date  = dateFormat.format(calendar.getTime());

        currentDate = date;
        if(isTradeDate()) {
            setChanged();
            notifyObservers(currentDate);
        }
        else{
            logger.info(currentDate + "---- 非交易日 ------");
        }
        return currentDate;
    }

    public Map<String, String> getData(String code, String toDate) throws Exception {
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

    public Map<String, String> getCurrentData(String code) throws Exception {
        return getData(code, currentDate);
    }


    public List<Map<String, String>> getHistory(String code, String toDate) throws Exception {

        Map stockInfo = getStockInfo(code);
        if(stockInfo==null){
            logger.error("Nod found the stock code:'" +code +"'");
            return null;
        }

        List<Map<String,String>> list;
        if(histories.get(code)!=null){
            list = histories.get(code);
        }
        else{
            String name = (String) getStockInfo(code).get(STOCK_NAME);
            list = getDataProvider().getList(HISTORY, code+"-"+name );
            if(list!=null) {
                Collections.sort(list, new Comparator<Map<String, String>>() {

                    @Override
                    public int compare(Map<String, String> o1, Map<String, String> o2) {
                        String code1 = o1.get("DATE");
                        String code2 = o2.get("DATE");
                        return code1.compareTo(code2);
                    }

                });
                histories.put(code, list);
            }

        }
        List<Map<String,String>> ret = new ArrayList<Map<String, String>>();
        int icDate = getIntDate(toDate);
        if(list!=null){
            for(Map<String,String> m : list) {
                String date = m.get(DATE);
                int iDate = getIntDate(date);

                if (iDate > icDate) {
                    break;
                }
                String vol = m.get(VOLUME);
                if(vol!=null && !("000").equals(vol)){
                    ret.add(m);
                }

            }
        }
        return ret;
    }
    public List<Map<String, String>> getHistory(String code) throws Exception {
        return getHistory(code, currentDate);
    }

    public synchronized  void setHistory(String code, List<Map<String, String>> history) throws Exception {
        histories.remove(code);
        Collections.sort(history, new Comparator<Map<String, String>>() {

            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                String code1 = o1.get("DATE");
                String code2 = o2.get("DATE");
                return code1.compareTo(code2);
            }

        });
        histories.put(code, history);
    }

    public List<Map<String, String>> getAllHistory(String code) throws Exception {
        List<Map<String,String>> h = histories.get(code);
        if(h==null){
            String name = (String) getStockInfo(code).get(STOCK_NAME);
            h =  getDataProvider().getList(HISTORY, code+"-"+name );
            histories.put(code, h);
        }
        return h;
    }

    public void order(Agent agent, String stockCode, int buyOrSell, double unit, double price){

    }
}
