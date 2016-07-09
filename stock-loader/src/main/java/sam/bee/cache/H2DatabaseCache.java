package sam.bee.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public class H2DatabaseCache extends AbstractCache {

	@Override
	public void set(List<Map<String, String>> values, String... keys)
			throws Exception {
		set(JsonHelper.toJSON(values).toString(), keys);
	}
	
	@Override
	public List<Map<String, String>> getList(String... keys) throws Exception {
		
		List ret = new ArrayList();
		if(keys.length==2){
			String sql = "SELECT CODE, DESC FROM "  +  keys[1];
			Connection conn = openConnection(keys[0]);
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs  = st.executeQuery();
			Map map;
			while(rs.next()){
				map = new HashMap();
				map.put("CODE",rs.getString("CODE"));
				map.put("DESC",rs.getString("DESC"));
				ret.add(map);
			}
			 closeConnection(conn);
			 return ret;
		}
		else{
		 String data = getString(keys);
		 if(data!=null && data.length()>0){
			 return JsonHelper.toList( new JSONArray(data));
		 }
		}
		 return ret;
		 
	}
	
	@Override
	public void set(String value, String... keys) throws Exception{
	
		if(keys.length!=3){
			throw new RuntimeException("please set keys's length equals 3, thie 1st is database, the 2nd is table name, 3nd is code");
		}
		
		Connection conn = openConnection(keys[0]);
		createTablIfExist(conn, keys[1].toUpperCase());
		
		
		String sql = "DELETE FROM "  +  keys[1] + " WHERE CODE=?";
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, keys[2]);
		st.execute();
		
		sql = "INSERT INTO "  +  keys[1] + " VALUES(?,?)";		
		st = conn.prepareStatement(sql);
		st.setString(1, keys[2]);
		st.setString(2, value);
		//st.setString(3, value);
		st.execute();
	    closeConnection(conn);
	}
	
	public String getString(String... keys) throws Exception{
		String sql = "SELECT DESC FROM "  +  keys[1] + " WHERE CODE=?";
		Connection conn = openConnection(keys[0]);
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, keys[2]);
		ResultSet rs  = st.executeQuery();
		String ret = "";
		while(rs.next()){
			ret = rs.getString(1);
		}
		 closeConnection(conn);
		 return ret;
	}

	
	private void createTablIfExist(Connection conn, String tableName) throws SQLException{
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName+ " (CODE VARCHAR(10) PRIMARY KEY, DESC TEXT);";
		conn.prepareStatement(sql).execute();
	}
	
	private Connection openConnection(String database) throws ClassNotFoundException, SQLException{		
		   Class.forName("org.h2.Driver");
		   return DriverManager.
	            getConnection("jdbc:h2:~/" +database, "sa", "");
	        
	}
	
	
	private void closeConnection(Connection conn){		
		 if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	}

}
