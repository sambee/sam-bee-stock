package sam.bee.stock.loader;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sam.bee.stock.loader.impl.GetStockNameAndFileCacheTest;
import sam.bee.stock.loader.impl.RealTimeDataApapterTest;
import sam.bee.stock.loader.impl.GetStockHistoryTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	GetStockNameAndFileCacheTest.class,
	RealTimeDataApapterTest.class,
	GetStockHistoryTest.class
})
public class LoaderSutie {

	
}
