package init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.iapi.util.StringUtil;

/**
 * �����ʼ�����ݵ����ݿ�
 */
public class InitDbDataCmd {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// ����Derby����
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		// �������ӣ�����������Ϊmydb�����ݿ⣨����һ�μ��ɣ�
		try (Connection con = DriverManager.getConnection("jdbc:derby:mydb;create=true")) {
			try (Statement stmt = con.createStatement()) {
				// ���������
				// createChengYuTable(stmt);

				// �������
				// insertChengYuData(stmt);

				// ��ѯ��������
				// queryChengYuData(stmt);

				// �������а��
				//createRankTable(stmt);
			}
		}

	}

	private static void insertRankListData(Statement stmt) throws SQLException  {
		stmt.executeUpdate("insert into rank_list(name, score) values('owl', 102)");
		stmt.executeUpdate("insert into rank_list(name, score) values('zzz', 20)");
		stmt.executeUpdate("insert into rank_list(name, score) values('xxx', 50)");
		stmt.executeUpdate("insert into rank_list(name, score) values('www', 40)");
		System.out.println("����ɹ�!");
	}

	private static void createRankTable(Statement stmt) throws SQLException {
		String sql = "create table rank_list(" 
	                     + "name varchar(128)," 
				         + "score int" 
	                     + ")";
		stmt.execute(sql);
		System.out.println("�����ɹ�!");
	}

	private static void queryChengYuData(Statement stmt) throws SQLException {
		String sql = "select word from cheng_yu";
		try (ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				System.out.println(rs.getString("word"));
			}
		}
	}

	private static void insertChengYuData(Statement stmt) throws IOException, SQLException {
		// ��ɾ�����м�¼
		String delSql = "delete from cheng_yu";
		int delCount = stmt.executeUpdate(delSql);
		System.out.println("ɾ���ɹ���ɾ����¼��:" + delCount);

		File file = new File("C:/Users/Qin/Desktop/ChengYuJieLong/ci_ku_export.txt");
		int insertCount = 0;
		try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))) {
			String word = null;
			while ((word = bReader.readLine()) != null) {
				word = word.trim();
				if ("".equals(word)) {
					continue;
				}

				stmt.executeUpdate("insert into cheng_yu(word) values('" + word + "')");
				++insertCount;
				System.out.println("�������:" + word);
			}

		}

		System.out.println("����ɹ�,�����¼����" + insertCount);
	}

	/**
	 * ���������
	 * 
	 * @param stmt
	 * @throws SQLException
	 */
	private static void createChengYuTable(Statement stmt) throws SQLException {
		String sql = "create table cheng_yu(" + "word varchar(10)," + "constraint P_KEY_1 primary key (word) " + ")";
		// ���������
		stmt.execute(sql);

		System.out.println("�����ɹ�!");
	}

}
