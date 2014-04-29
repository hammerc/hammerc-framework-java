/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.io;

/**
 * 定义了可以进行序列化和反序列化的数据类型.
 * @author wizardc
 */
public interface IExternalizable
{
	/**
	 * 将其自身编码到数据流中.
	 * @param output 数据输出对象.
	 * @throws Exception 会抛出的异常.
	 */
	public void writeExternal(IDataOutput output) throws Exception;
	
	/**
	 * 将其自身从数据流中解码.
	 * @param input 数据输入对象.
	 * @throws Exception 会抛出的异常.
	 */
	public void readExternal(IDataInput input) throws Exception;
}
