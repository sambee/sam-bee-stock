package com.sam.stock.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class DBUtils {

	
	public static Map save(String tableName, Map params){
		return null;
	}
	
	public static Map get(String tableName, Map params){
		return null;
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

	}	
	
	public static void test2() throws ClassNotFoundException, SQLException{
		Connection conn = DBHelper.getConnection("stock.mdb");
		PreparedStatement ps = conn.prepareStatement("Select * from stock_info");		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getInt("id"));
		}
	}
	
	public static void test1() throws IOException, SQLException{
		
		String[] fields ={"id", "code", "name", "type"};
		
		Database db = DBHelper.createDatabase("stock.mdb");		
		Table tb = DBHelper.createTable(db, "stock_info", fields);
		for(int i=0; i<200000; i++){
			System.out.println(i);
			DBHelper.addRecord(tb, i, "2", "3");
		}		
		System.exit(0);
	}
}

class DBHelper{
	
	public static Connection getConnection(String filePath) throws ClassNotFoundException, SQLException{		
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");      
	    return DriverManager.getConnection("jdbc:ucanaccess://" + filePath);
	}
	
	public static Database createDatabase(String name)  throws IOException {
		String filePath = name;
		File file =	new File(filePath);
		if(file.exists()){
			file.deleteOnExit();
		}
		return DatabaseBuilder.create(Database.FileFormat.V2000, file);
	}

	public static Table createTable(Database db, String tableName, String[] fields) throws IOException, SQLException {
		TableBuilder tb = new TableBuilder(tableName);
		
		
		for(String f : fields){
			if("id".equals(f)){
				 tb = tb.addColumn(new ColumnBuilder(f)
		             .setSQLType(Types.INTEGER));
			}
			else{
				tb = tb.addColumn(new ColumnBuilder(f)
	             .setSQLType(Types.VARCHAR));
			}
		}
		
		
		 
		return tb.toTable(db);
		//newTable.addRow(1, "foo");
	}
	
	@SuppressWarnings("unchecked")
	public static void addRecord(Table tb, Map params) throws IOException{
		tb.addRowFromMap(params);
	}
	
	public static void addRecord(Table tb,Object... params) throws IOException{	
			tb.addRow(params);
		
	}
}
