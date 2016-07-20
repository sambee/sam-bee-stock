package sam.bee.stock.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sam.bee.porvider.CSVDataProvider;
import sam.bee.porvider.IDataProvider;

public abstract class BasicTest {

	protected static final Logger logger = LoggerFactory.getLogger(BasicTest.class);

	public String getTestCode(){
		return "600578";
	}
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

	private IDataProvider dataProvider;
	public IDataProvider getDataProvider(){
		if(dataProvider==null){
			dataProvider = new CSVDataProvider();
		}
		return dataProvider;
	}
}
