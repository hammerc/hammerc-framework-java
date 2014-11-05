// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseUtils 类包含执行 MySQL 数据库查询等操作的方法.
 * @author wizardc
 */
public class DatabaseUtils
{
	/**
	 * 执行会返回结果的查询数据库操作.
	 * @param pool 执行操作的连接池对象.
	 * @param sql 需要执行的 SQL 语句.
	 * @return 查询结果.
	 * @throws SQLException 查询数据库失败时抛出该异常.
	 * @throws InterruptedException 如果在等待中被中断时抛出该异常.
	 */
	public static QueryResult executeQuery(ConnectionPool pool, String sql) throws InterruptedException, SQLException
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try
		{
			connection = pool.getConnection();
			statement = (Statement) connection.createStatement();
			resultSet = statement.executeQuery(sql);
			QueryResult queryResult = new QueryResult(resultSet);
			return queryResult;
		}
		finally
		{
			if(resultSet != null)
			{
				resultSet.close();
			}
			if(statement != null)
			{
				statement.close();
			}
			if(connection != null)
			{
				pool.joinConnection(connection);
			}
		}
	}
	
	/**
	 * 执行不需要返回结果的数据库查询操作.
	 * @param pool 执行操作的连接池对象.
	 * @param sql 需要执行的 SQL 语句.
	 * @throws SQLException 查询数据库失败时抛出该异常.
	 * @throws InterruptedException 如果在等待中被中断时抛出该异常.
	 */
	public static void executeSQL(ConnectionPool pool, String sql) throws InterruptedException, SQLException
	{
		Connection connection = null;
		Statement statement = null;
		try
		{
			connection = pool.getConnection();
			statement = (Statement) connection.createStatement();
			statement.executeUpdate(sql);
		}
		finally
		{
			if(statement != null)
			{
				statement.close();
			}
			if(connection != null)
			{
				pool.joinConnection(connection);
			}
		}
	}
}
