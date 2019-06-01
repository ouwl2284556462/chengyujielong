package base;

import java.sql.ResultSet;

/**
 * 查询数据库列表 
 */

@FunctionalInterface
public interface DbResultSetIterator {
	/**
	 * 遍历结果集的每条记录
	 * @param rs
	 */
	void iterate(ResultSet rs) throws Exception;
}
