package base;

import java.sql.ResultSet;

/**
 * ��ѯ���ݿ��б� 
 */

@FunctionalInterface
public interface DbResultSetIterator {
	/**
	 * �����������ÿ����¼
	 * @param rs
	 */
	void iterate(ResultSet rs) throws Exception;
}
