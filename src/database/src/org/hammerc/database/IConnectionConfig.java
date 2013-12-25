/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.database;

/**
 * IConnectionConfig 接口定义了连接数据库时需要连接池提供的配置对象.
 * @author wizardc
 */
public interface IConnectionConfig
{
	/**
	 * 获取要加载的驱动程序类.
	 * @return 要加载的驱动程序类.
	 */
	public String getDriverClass();
	
	/**
	 * 获取数据库的地址.
	 * @return 数据库的地址.
	 */
	public String getConnectionHost();
	
	/**
	 * 获取数据库的端口.
	 * @return 数据库的端口.
	 */
	public int getConnectionPort();
	
	/**
	 * 获取登录数据库的用户名.
	 * @return 登录数据库的用户名.
	 */
	public String getConnectionUsername();
	
	/**
	 * 获取登录数据库的密码.
	 * @return 登录数据库的密码.
	 */
	public String getConnectionPassword();
	
	/**
	 * 获取选择的数据库的名称.
	 * @return 选择的数据库的名称.
	 */
	public String getDatabase();
	
	/**
	 * 获取连接数据库时的其它传入参数, 以 & 开头.
	 * @return 连接数据库时的其它传入参数.
	 */
	public String getOtherParam();
	
	/**
	 * 获取用来连接数据库的连接字符串.
	 * @return 用来连接数据库的连接字符串.
	 */
	public String getConnectionString();
}
