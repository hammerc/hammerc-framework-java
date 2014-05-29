/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.database;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * QueryResult 类记录数据库查询后返回的数据, 并提供简单的访问接口.
 * @author wizardc
 */
public class QueryResult
{
	//记录所有字段的名称
	private HashMap<String, Integer> _columnNames;
	//记录所有的查询结果
	private ArrayList<ArrayList<Object>> _result;
	
	/**
	 * 创建一个查询结果.
	 * @param resultSet 需要处理的原生查询结果数据.
	 * @throws SQLException 获取查询记录的信息失败时会抛出该异常.
	 */
	public QueryResult(ResultSet resultSet) throws SQLException
	{
		_columnNames = new HashMap<String, Integer>();
		_result = new ArrayList<ArrayList<Object>>();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnSize = metaData.getColumnCount();
		for(int i = 0; i < columnSize; i++)
		{
			_columnNames.put(metaData.getColumnLabel(i + 1).toLowerCase(), i);
		}
		while(resultSet.next())
		{
			ArrayList<Object> item = new ArrayList<Object>();
			for(int i = 0; i < columnSize; i++)
			{
				item.add(resultSet.getObject(i + 1));
			}
			_result.add(item);
		}
	}
	
	/**
	 * 获取查询结果的数据总数.
	 * @return 查询结果的数据总数.
	 */
	public int size()
	{
		return _result.size();
	}
	
	/**
	 * 判断一个字段的名称是否存在.
	 * @param columnName 需要查找的字段的名称.
	 * @return 该字段名称是否存在.
	 */
	public boolean hasColumnName(String columnName)
	{
		return _columnNames.containsKey(columnName.toLowerCase());
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public boolean getBoolean(int row, String columnName)
	{
		return (Boolean) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public short getShort(int row, String columnName)
	{
		return (Short) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public int getInteger(int row, String columnName)
	{
		return (Integer) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public long getLong(int row, String columnName)
	{
		return (Long) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public BigInteger getBigInteger(int row, String columnName)
	{
		return (BigInteger) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public float getFloat(int row, String columnName)
	{
		return (Float) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public double getDouble(int row, String columnName)
	{
		return (Double) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public BigDecimal getBigDecimal(int row, String columnName)
	{
		return (BigDecimal) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public String getString(int row, String columnName)
	{
		return (String) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public Date getDate(int row, String columnName)
	{
		return (Date) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public Time getTime(int row, String columnName)
	{
		return (Time) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public Timestamp getTimestamp(int row, String columnName)
	{
		return (Timestamp) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public byte[] getBytes(int row, String columnName)
	{
		return (byte[]) _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
	
	/**
	 * 获取一个指定的查询结果.
	 * @param row 该结果的行数.
	 * @param columnName 该结果位于的字段名称.
	 * @return 指定的查询结果.
	 */
	public Object getObject(int row, String columnName)
	{
		return _result.get(row).get(_columnNames.get(columnName.toLowerCase()));
	}
}
