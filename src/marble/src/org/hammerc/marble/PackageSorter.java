/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.marble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 导入包格式化类, 用于代码生成工具中.
 * @author wizardc
 */
public class PackageSorter implements Cloneable
{
	/**
	 * 包名记录列表.
	 */
	protected ArrayList<String> _packageList;
	
	/**
	 * 构造函数.
	 */
	public PackageSorter()
	{
		_packageList = new ArrayList<String>();
	}
	
	/**
	 * 获取长度.
	 */
	public int size()
	{
		return _packageList.size();
	}
	
	/**
	 * 添加一个包名.
	 * @param value 包名.
	 */
	public void addPackage(String value)
	{
		if(_packageList.indexOf(value) == -1)
		{
			_packageList.add(value);
		}
	}
	
	/**
	 * 判断指定包名是否存在.
	 * @param value 包名.
	 * @return 指定包名是否存在.
	 */
	public boolean hasPackage(String value)
	{
		return _packageList.contains(value);
	}
	
	/**
	 * 移除一个包名.
	 * @param value 包名.
	 */
	public void removePackage(String value)
	{
		if(_packageList.indexOf(value) != -1)
		{
			_packageList.remove(value);
		}
	}
	
	/**
	 * 获取格式化的导入包文本,
	 * @param useSpace 每个包名前面是使用空格还是使用 Tab.
	 * @param numSpace 每个包名前面使用的空格或 Tab 的个数.
	 * @param useSeparatorLine 是否根据包的根目录添加空行.
	 * @param lineSeparator 使用的换行符, 设置为 null 使用系统默认的换行符.
	 * @return 格式化的导入包文本,
	 */
	public String getPackageString(boolean useSpace, int numSpace, boolean useSeparatorLine, String lineSeparator)
	{
		if(_packageList.size() == 0)
		{
			return "";
		}
		if(lineSeparator == null)
		{
			lineSeparator = System.getProperty("line.separator", "\r\n");
		}
		ArrayList<String> packageList = new ArrayList<String>();
		Iterator<String> iterator;
		iterator = _packageList.iterator();
		while(iterator.hasNext())
		{
			String item = iterator.next();
			item = item.replaceAll(";", "");
			item = item.trim();
			item = item.replaceAll("\\s{2,}", " ");
			packageList.add(item);
		}
		Collections.sort(packageList);
		if(useSeparatorLine)
		{
			ArrayList<String> list = new ArrayList<String>();
			String lastName, nowName;
			lastName = getFristPackageName(packageList.get(0));
			iterator = packageList.iterator();
			while(iterator.hasNext())
			{
				String item = iterator.next();
				nowName = getFristPackageName(item);
				if(!lastName.equals(nowName))
				{
					list.add("");
					lastName = nowName;
				}
				list.add(item);
			}
			packageList = list;
		}
		StringBuffer space = new StringBuffer();
		while(numSpace > 0)
		{
			space.append(useSpace ? " " : "\t");
			--numSpace;
		}
		String spaceStr = space.toString();
		StringBuffer result = new StringBuffer();
		boolean useLineSeparator = false;
		iterator = packageList.iterator();
		while(iterator.hasNext())
		{
			String item = iterator.next();
			if(item.length() > 0)
			{
				item = item + ";";
			}
			if(useLineSeparator)
			{
				result.append(lineSeparator + spaceStr + item);
			}
			else
			{
				result.append(spaceStr + item);
			}
			useLineSeparator = true;
		}
		return result.toString();
	}
	
	private String getFristPackageName(String value)
	{
		int index1 = value.indexOf(" ");
		int index2 = value.indexOf(".");
		return value.substring(index1, index2);
	}
	
	/**
	 * 清除所有包名.
	 */
	public void clear()
	{
		_packageList.clear();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		PackageSorter result;
		result = (PackageSorter) super.clone();
		result._packageList = (ArrayList<String>) _packageList.clone();
		return result;
	}
}
