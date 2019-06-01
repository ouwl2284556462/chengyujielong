package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ����Derby���ݿ�
 */
public class TestDerby {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//����Derby����
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		//�������ӣ�����������Ϊmydb�����ݿ⣨����һ�μ��ɣ�
		//Connection con = DriverManager.getConnection("jdbc:derby:mydb;create=true");
		Connection con = DriverManager.getConnection("jdbc:derby:mydb;");
		Statement stmt = con.createStatement();
		
		//���������
		//stmt.execute("CREATE TABLE test(name VARCHAR(10))");
		
		//�����������
		//stmt.execute("insert into test(name) values('aaa')");
		stmt.execute("insert into test(name) values('����')");
		
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
