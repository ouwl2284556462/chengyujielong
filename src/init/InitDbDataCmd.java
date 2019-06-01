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
 * 插入初始化数据到数据库
 */
public class InitDbDataCmd {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// 加载Derby驱动
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

		// 创建连接，并创建名称为mydb的数据库（创建一次即可）
		try (Connection con = DriverManager.getConnection("jdbc:derby:mydb;create=true")) {
			try (Statement stmt = con.createStatement()) {
				// 创建成语表
				// createChengYuTable(stmt);

				// 插入成语
				// insertChengYuData(stmt);

				// 查询成语数据
				// queryChengYuData(stmt);

				// 创建排行榜表
				//createRankTable(stmt);
			}
		}

	}

	private static void insertRankListData(Statement stmt) throws SQLException  {
		stmt.executeUpdate("insert into rank_list(name, score) values('owl', 102)");
		stmt.executeUpdate("insert into rank_list(name, score) values('zzz', 20)");
		stmt.executeUpdate("insert into rank_list(name, score) values('xxx', 50)");
		stmt.executeUpdate("insert into rank_list(name, score) values('www', 40)");
		System.out.println("插入成功!");
	}

	private static void createRankTable(Statement stmt) throws SQLException {
		String sql = "create table rank_list(" 
	                     + "name varchar(128)," 
				         + "score int" 
	                     + ")";
		stmt.execute(sql);
		System.out.println("创建成功!");
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
		// 先删除所有记录
		String delSql = "delete from cheng_yu";
		int delCount = stmt.executeUpdate(delSql);
		System.out.println("删除成功，删除记录数:" + delCount);

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
				System.out.println("插入成语:" + word);
			}

		}

		System.out.println("插入成功,插入记录数：" + insertCount);
	}

	/**
	 * 创建成语表
	 * 
	 * @param stmt
	 * @throws SQLException
	 */
	private static void createChengYuTable(Statement stmt) throws SQLException {
		String sql = "create table cheng_yu(" + "word varchar(10)," + "constraint P_KEY_1 primary key (word) " + ")";
		// 创建成语表
		stmt.execute(sql);

		System.out.println("创建成功!");
	}

}
