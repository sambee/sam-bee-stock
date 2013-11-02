package sam.bee.stock.loader;
import java.util.List;
import java.util.Map;

public interface IStockService {
	
	List<Map<String, ?>> getCurrentStock(List<String> stockCode);
}
