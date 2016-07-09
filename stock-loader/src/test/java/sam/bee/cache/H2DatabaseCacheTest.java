package sam.bee.cache;

import static org.junit.Assert.*;

import org.junit.Test;

public class H2DatabaseCacheTest {

	@Test
	public void testH2DatabaseCache() throws Exception {
		ICache h2 = new H2DatabaseCache();
		String s = "";
		for(int i=0; i<10000; i++){
			s += i;
		}
		for(int i=0; i<2000; i++){
			h2.set(s, "60000"+i);
		}
	}

}
