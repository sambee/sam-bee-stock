package sam.bee.stock.loader.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sam.bee.stock.loader.BasicTest;


public class GetRealTimeStockTest extends BasicTest {


	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		

		String code = "";

		List<Map<String, String>> stockList = new 	QQRealTimLoader().execute(code, "sz");


	}

}
