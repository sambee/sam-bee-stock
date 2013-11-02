package sam.bee.stock.loader;

import java.util.List;

public interface IDataAdapter {

	Object parse(List<?> list);
}
