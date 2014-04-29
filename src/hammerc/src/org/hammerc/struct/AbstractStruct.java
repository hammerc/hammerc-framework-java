/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.struct;

import org.hammerc.io.IDataInput;
import org.hammerc.io.IDataOutput;
import org.hammerc.io.IExternalizable;

import java.nio.ByteOrder;

/**
 * 可以写入字节流和从字节流中读取的自定义数据类.
 * @author wizardc
 */
public abstract class AbstractStruct implements IExternalizable
{
	/**
	 * 自定义数据类的编码字节序.
	 */
	public static ByteOrder STRUCT_ENDIAN = ByteOrder.BIG_ENDIAN;
	
	/**
	 * 构造函数.
	 */
	public AbstractStruct()
	{
	}
	
	@Override
	final public void writeExternal(IDataOutput output) throws Exception
	{
		output.endian(STRUCT_ENDIAN);
		this.writeToBytes(output);
	}
	
	/**
	 * 编码本对象.
	 * @param output 输出流对象.
	 * @throws Exception 会抛出的异常.
	 */
	protected abstract void writeToBytes(IDataOutput output) throws Exception;
	
	@Override
	final public void readExternal(IDataInput input) throws Exception
	{
		input.endian(STRUCT_ENDIAN);
		this.readFromBytes(input);
	}
	
	/**
	 * 解码本对象.
	 * @param input 输入流对象.
	 * @throws Exception 会抛出的异常.
	 */
	protected abstract void readFromBytes(IDataInput input) throws Exception;
}
