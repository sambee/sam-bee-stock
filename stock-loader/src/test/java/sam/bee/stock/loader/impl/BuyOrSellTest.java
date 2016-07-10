package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;  
import static org.mockito.Mockito.*;
import static sam.bee.stock.indicator.MACD.macd;

import java.util.*;

import org.junit.Test;
import org.slf4j.Marker;
import sam.bee.stock.loader.BaseTest;
import sam.bee.stock.trade.DataUtil;
import sam.bee.stock.trade.Market;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BuyOrSellTest extends BaseTest{
//	 @Test
//	    public void simpleTest(){
//	        //arrange
//	        Iterator i=mock(Iterator.class);
//	        when(i.next()).thenReturn("Hello").thenReturn("World");
//	        //act
//	        String result=i.next()+" "+i.next();
//	        //verify
//	verify(i, times(2)).next();
//	        //assert
//	        assertEquals("Hello World", result);
//	    }

	@Test
	public void test() throws Exception {
		Market market = new Market();
		String date;
		for(int i=0;i<100;i++){
//			Map m = history.get(history.size()-1-i);

			List<Map<String, String>>  history = market.getHistory(getTestCode());
			Map<String, String> dm = history.get(history.size()-1);
			date = market.getCurrentDate();
			String tradeDate = dm.get("DATE");
			if(date.equals(tradeDate)) {
				System.out.println(dm);
				Double[] close = DataUtil.getClose(history);
				List d = Arrays.asList(close);
				HashMap<String, Double> result = macd(d, 2, 16, 9);
				System.out.println(market.getCurrentDate() + "  " + result);
			}
			market.next();
		}


	}
}
