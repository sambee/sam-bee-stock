package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GetAllShangHaiStockListTest {

	@Test
	public void test() throws IOException {
		GetAllShangHaiStockList list = new GetAllShangHaiStockList();
		List<Map<String,String>> p = list.list();
		System.out.println(p);
		
	}

}
