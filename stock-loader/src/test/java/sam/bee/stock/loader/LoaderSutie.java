package sam.bee.stock.loader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sam.bee.stock.loader.impl.GetStockNameAndProviderTest;
import sam.bee.stock.loader.impl.QQRealTimeDataQueryTest;
import sam.bee.stock.loader.impl.GetStockHistoryTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	GetStockNameAndProviderTest.class,
	QQRealTimeDataQueryTest.class,
	GetStockHistoryTest.class
})
public class LoaderSutie {

	
}
