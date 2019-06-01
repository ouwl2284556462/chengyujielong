package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 测试Derby数据库
 */
public class TestDerby {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//加载Derby驱动
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		//创建连接，并创建名称为mydb的数据库（创建一次即可）
		//Connection con = DriverManager.getConnection("jdbc:derby:mydb;create=true");
		Connection con = DriverManager.getConnection("jdbc:derby:mydb;");
		Statement stmt = con.createStatement();
		
		//创建表测试
		//stmt.execute("CREATE TABLE test(name VARCHAR(10))");
		
		//插入测试数据
		//stmt.execute("insert into test(name) values('aaa')");
		stmt.execute("insert into test(name) values('中文')");
		
		String sql = "select name from test";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			System.out.println(rs.getString("name"));
		}
		
		rs.close();
		stmt.close();
		con.close();
		
		System.exit(0);
	}

}
