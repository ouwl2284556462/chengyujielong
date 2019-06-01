package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbFactory {
	private static Connection dbConnect;
	
	public synchronized static Connection getDbConnection() throws SQLException, ClassNotFoundException{
		if(null == dbConnect){
			//加载Derby驱动
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Connection con = DriverManager.getConnection("jdbc:derby:mydb;create=true");
			dbConnect = con;
		}
		
		return dbConnect;
	}
	
	/**
	 * 数据库是否已经初始化了
	 * @return
	 */
	public static boolean hasDatabaseInit(){
		try {
			Connection conn = getDbConnection();
			try(Statement stm = conn.createStatement()){
				stm.executeQuery("select 1 from rank_list where 1=2");
			}catch(Exception e){
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	public synchronized static void releaseConn() throws SQLException{
		if(null == dbConnect){
			return;
		}
		
		if(dbConnect.isClosed()){
			return;
		}
		
		dbConnect.close();
		dbConnect = null;
	}
}
