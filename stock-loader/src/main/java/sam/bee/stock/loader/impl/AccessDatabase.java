package sam.bee.stock.loader.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AccessDatabase {

	private static final Logger log = LoggerFactory
			.getLogger(AccessDatabase.class);

	Database accessDB;
//	Connection conn;
	public AccessDatabase(File mdbFile) throws Exception{
		if (!mdbFile.exists()) {
			accessDB = createDatabase(mdbFile);
		} else {
			accessDB = DatabaseBuilder.open(mdbFile);
		}
//		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//		conn = DriverManager.getConnection("jdbc:ucanaccess://" + mdbFile.getCanonicalPath(),"", "");

	}

	private Database createDatabase(File dbFile) throws IOException {
		return DatabaseBuilder.create(Database.FileFormat.V2000, dbFile);
	}

	private TableBuilder createTable(String tableName) {
		return new TableBuilder(tableName);
	}

	public void createAccessTable(String tableName,
			List<Map<String, Object>> fieldsInfo) throws IOException,
			SQLException {
		if (accessDB == null) {
			throw new NullPointerException("Database base is not opened.");
		}
		TableBuilder tableBuilder = createTable(tableName);
		for (Map<String, Object> f : fieldsInfo) {
			tableBuilder
					.addColumn(new ColumnBuilder((String) f.get("COL_NAME"))
							.setSQLType(getType((String) f.get("COL_TYPE")))
							.toColumn());
		}
		tableBuilder.toTable(accessDB);
	}

	public int getType(String type) {
		if ("varchar".equals(type)) {
			return Types.LONGVARCHAR;
		}
		if ("nvarchar".equals(type)) {
			return Types.LONGVARCHAR;
		}
		if ("ntext".equals(type)) {
			return Types.BLOB;
		}
		if ("numeric".equals(type)) {
			return Types.INTEGER;
		}
		if ("datetime".equals(type)) {
			return Types.TIMESTAMP;
		}
		if ("decimal".equals(type)) {
			return Types.INTEGER;
		}
		if ("int".equals(type)) {
			return Types.INTEGER;
		}
		throw new RuntimeException("Unkonw " + type + " sql  type.");
	}

	public void addRowFromMap(String tableName, Map<String, Object> data)
			throws IOException {
		accessDB.getTable(tableName).addRowFromMap(data);
	}

	public List<Map<String, Object>> list(String tableName) throws IOException {
		List list = new ArrayList();
		Table table = accessDB.getTable(tableName);
		List<? extends Column> cols = table.getColumns();

		Map colMap;
		for (Row row : table) {
			colMap = new HashMap();
			for (Column col : cols) {
				colMap.put(col.getName(), row.get(col.getName()));

			}
			list.add(colMap);
		}
		return list;
	}

	public void close() throws IOException {
		if (accessDB != null) {
			accessDB.close();
		}
	}

	public void updateRowFromMap(final String tableName, final String keyName, final String keyValue,final Map<String, Object> rowMap) throws IOException {
		if(keyName==null || keyName.isEmpty() || keyValue==null || keyValue.isEmpty())
			return;
		Table table = accessDB.getTable(tableName);
		Cursor cursor = CursorBuilder.createCursor(table);
		while (cursor.moveToNextRow()) {
			Row row =  cursor.getCurrentRow();
			Object obj =row.get(keyName);
			if (keyValue.equals(obj)) {				
				//log.info(row + String.valueOf( rowMap));
				cursor.updateCurrentRowFromMap(rowMap);
				return;
			}
		}

	}
	
//	public void updateRowFromMap(String tableName, String keyName,	String keyValue, Map<String, Object> rowMap) throws Exception {
//		String insert = "insert into " + keyName + " (STOCK_PRICE) values (?) where STOCK_CODE=?";
//		PreparedStatement pstmt  = conn.prepareStatement(insert);
//		String stockPrice =(String) rowMap.get("STOCK_PRICE");
//		pstmt.setString(1, stockPrice);
//		pstmt.setString(1, keyValue);
//		boolean isDone = pstmt.execute();
//		if(!isDone){
//			System.err.print("----------------ERROR -------");
//			System.exit(0);
//		}
//
//	}
}
