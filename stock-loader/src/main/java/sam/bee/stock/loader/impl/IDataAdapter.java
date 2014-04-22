package sam.bee.stock.loader.impl;

import java.util.List;

public interface IDataAdapter {

	Object parse(List<?> list);
}
