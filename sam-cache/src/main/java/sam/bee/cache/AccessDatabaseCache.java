package sam.bee.cache;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
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
import com.healthmarketscience.jackcess.util.ImportUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AccessDatabaseCache extends AbstractCache{

	private static final Logger log = LoggerFactory
			.getLogger(AccessDatabaseCache.class);

	File mdbFile = null;
	Database accessDB;
//	Connection conn;
	public AccessDatabaseCache(File mdbFile) throws Exception{
		if (!mdbFile.exists()) {
			accessDB = createDatabase(mdbFile);
		} else {
			accessDB = DatabaseBuilder.open(mdbFile);
		}
		this.mdbFile = mdbFile; 
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

	public void emptyTable(String tableName) throws IOException{
		
		log.info("Empty table:" + tableName);
		Table table = accessDB.getTable(tableName);		
		Cursor cursor = CursorBuilder.createCursor(table);
		while(cursor.moveToNextRow()){
			cursor.deleteCurrentRow();
			log.info("Delete:" + table.getRowCount());			
		}
		
//		for(Row row : table) {
//			log.info("Delete "+ table.getRowCount());
//			//Cursor cursor = tb.getDefaultCursor();
//			//Row row = cursor.getCurrentRow();
//			table.deleteRow(row);
//		}
		
		if(table.getRowCount()!=0){
			throw new RuntimeException("Not empty");
		}
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
		log.info("Add data" + data);
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
	
	public void deleteByColumnName(String tableName, String key, String value) throws IOException{
		Table table = accessDB.getTable(tableName);
		Cursor cursor = CursorBuilder.createCursor(table);
		
		cursor.deleteCurrentRow();		 
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
	
	public  void createTableRecByCsv(String talbeName, File thisCsv) throws IOException{
		Database db = DatabaseBuilder.open(mdbFile);
		new ImportUtil.Builder(db, talbeName).setDelimiter(",").importFile(thisCsv);
		db.close();
	}

	@Override
	public void set(String value, String... key) throws Exception {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public String getString(String... key) throws Exception {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public void set(List<Map<String, String>> list, String... keys) throws Exception
	{
		String json = JsonHelper.toJSON(list).toString();
		set(json, keys);
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public List<Map<String, String>> getList(String... key) throws Exception {
		throw new RuntimeException("Not supported yet.");
	}

	@Override
	public boolean exist(String... key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existIgnoreTime(String... key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cleanCache(String... key) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException { 
		// getAccessDataTable();
		//createTable(); 
		//createTableRecByCsv() ;
		deleteRec();
	}
	/**
	 * 读取指定表格
	 * @throws IOException
	 */
	public static void getAccessDataTable() throws IOException{
		Table table = DatabaseBuilder.open(new File("c:\\data.mdb")).getTable("AspCms_Collect");
		for(Row row : table) {
		  System.out.println("--ID--" + row.get("CollectID")+"--Name--" + row.get("CollectName"));
		}
	}
	/**
	 * 创建表并写入数据
	 * @throws IOException
	 */
	public static void createTable() throws IOException{
		 Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File("c:\\new.mdb"));
		 Table newTable;
		try {
			newTable = new TableBuilder("NewTable")
			   .addColumn(new ColumnBuilder("a")
			              .setSQLType(Types.INTEGER))
			   .addColumn(new ColumnBuilder("b")
			              .setSQLType(Types.VARCHAR))
			   .toTable(db);
			 newTable.addRow(1, "foo"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *  将CSV文件的内容复制到一个新表：
	 * @throws IOException
	 */
	public  static void createTableRecByCsv() throws IOException{
		Database db = DatabaseBuilder.open(new File("c:\\new.mdb"));
		new ImportUtil.Builder(db, "NewTable22").setDelimiter(",").importFile(new File("c:\\test.csv"));
		db.close();
	}
	/**
	 * 删除一条数据
	 * @throws IOException
	 */
	public static void deleteRec() throws IOException{
		Table table = DatabaseBuilder.open(new File("c:\\new.mdb")).getTable("NewTable22");
		Row row = CursorBuilder.findRow(table, Collections.singletonMap("xh", "4"));
		if(row != null) {
		  System.out.println("Found row where 'a' == 'foo': " + row);
		  table.deleteRow(row);
		} else {
		  System.out.println("Could not find row where 'a' == 'foo'");
		}
	}
}
