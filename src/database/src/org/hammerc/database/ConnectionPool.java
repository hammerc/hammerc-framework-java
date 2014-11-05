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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ConnectionPool 类定义了一个数据库连接池, 该连接池中的连接数不会超过指定的数量且可重复使用.
 * @author wizardc
 */
public class ConnectionPool
{
	//记录当前的数据库连接配置
	private IConnectionConfig _config;
	//记录当前的连接池
	private BlockingQueue<Connection> _pool;
	
	/**
	 * 创建连接池, 该连接池会始终保证连接数量小于等于参数 size 设置的数.
	 * @param config 当前连接池的数据库配置对象.
	 * @param size 该连接池的大小, 小于 1 时按 5 进行设置. 如果操作正确该大小为该连接池的最大大小.
	 * @throws ClassNotFoundException 当加载的驱动程序类不存在时抛出该异常.
	 * @throws SQLException 当初始化连接池失败时抛出该异常.
	 */
	public ConnectionPool(IConnectionConfig config, int size) throws ClassNotFoundException, SQLException
	{
		Class.forName(config.getDriverClass());
		_config = config;
		_pool = new LinkedBlockingQueue<Connection>();
		size = size < 1 ? 5 : size;
		for(int i = 0; i < size; i++)
		{
			_pool.add(createNewConnection());
		}
	}
	
	/**
	 * 获取一个可用的数据库连接, 如果该连接池已经空了时则等待有新的连接添加之前会一直阻塞, 取出的连接在使用后需要重新添加到该对象池中. 注意该方法线程安全但会导致线程阻塞.
	 * @return 返回一个可以使用的数据库连接.
	 * @throws InterruptedException 如果在等待中被中断时抛出该异常.
	 * @throws SQLException 创建新的连接失败时抛出该异常.
	 */
	public Connection getConnection() throws InterruptedException, SQLException
	{
		Connection connection = _pool.take();
		while(!connection.isValid(5))
		{
			connection.close();
			_pool.put(createNewConnection());
			connection = _pool.take();
		}
		return connection;
	}
	
	/**
	 * 创建一个新的可用的连接.
	 * @return 新的可用的连接.
	 * @throws SQLException 连接创建失败时抛出该异常.
	 */
	private Connection createNewConnection() throws SQLException
	{
		return DriverManager.getConnection(_config.getConnectionString());
	}
	
	/**
	 * 将使用过的连接重新放回该连接池.
	 * @param connection 要放回连接池的连接对象.
	 * @throws InterruptedException 如果在等待中被中断时抛出该异常.
	 */
	public void joinConnection(Connection connection) throws InterruptedException
	{
		if(connection != null)
		{
			_pool.put(connection);
		}
	}
	
	/**
	 * 获取连接池当前可用的连接的大小.
	 * @return 连接池当前可用的连接的大小.
	 */
	public int getSize()
	{
		return _pool.size();
	}
	
	/**
	 * 清空该连接池并关闭所有连接.
	 * @throws SQLException 关闭连接失败时抛出该异常.
	 */
	public void cleanup() throws SQLException
	{
		for(Connection connection : _pool)
		{
			connection.close();
			connection = null;
		}
		_pool.clear();
	}
}
