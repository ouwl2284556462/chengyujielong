package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import base.DbFactory;
import base.DbResultSetIterator;

/**
 * 数据访问基础类
 */
public class BaseDao {
	/**
	 * 遍历列表每一条记录
	 * 
	 * @param sql
	 * @param iterator
	 * @throws Exception
	 */
	protected void doQry(String sql, DbResultSetIterator iterator, Object... params) throws Exception {
		Connection conn = DbFactory.getDbConnection();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; ++i) {
				stmt.setObject(i + 1, params[i]);
			}

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					iterator.iterate(rs);
				}
			}
		}
	}

	protected int doUpdate(String sql, Object... params) throws Exception {
		Connection conn = DbFactory.getDbConnection();
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; ++i) {
				stmt.setObject(i + 1, params[i]);
			}

			return stmt.executeUpdate();
		}
	}
	
	protected void excuteSql(String sql) throws SQLException, ClassNotFoundException{
		Connection conn = DbFactory.getDbConnection();
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		}
	}

}
