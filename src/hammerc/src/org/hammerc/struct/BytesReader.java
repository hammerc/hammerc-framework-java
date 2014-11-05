// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.struct;

import org.hammerc.io.IDataInput;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * 提供从字节流中读取数据的功能.
 * @author wizardc
 */
public class BytesReader
{
	/**
	 * 读取一个布尔值.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static boolean readBoolean(IDataInput input)
	{
		return input.readByte() != 0;
	}
	
	/**
	 * 读取一个带符号 8 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static byte readByte(IDataInput input)
	{
		return input.readByte();
	}
	
	/**
	 * 读取一个无符号 8 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static short readUByte(IDataInput input)
	{
		return input.readUnsignedByte();
	}
	
	/**
	 * 读取一个带符号 16 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static short readShort(IDataInput input)
	{
		return input.readShort();
	}
	
	/**
	 * 读取一个无符号 16 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static int readUShort(IDataInput input)
	{
		return input.readUnsignedShort();
	}
	
	/**
	 * 读取一个带符号 32 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static int readInt(IDataInput input)
	{
		return input.readInt();
	}
	
	/**
	 * 读取一个无符号 32 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static long readUInt(IDataInput input)
	{
		return input.readUnsignedInt();
	}
	
	/**
	 * 读取一个带符号 64 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static long readLong(IDataInput input)
	{
		return input.readLong();
	}
	
	/**
	 * 读取一个无符号 64 位数字.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static BigInteger readULong(IDataInput input)
	{
		return input.readUnsignedLong();
	}
	
	/**
	 * 读取一个 32 位浮点数.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static float readFloat(IDataInput input)
	{
		return input.readFloat();
	}
	
	/**
	 * 读取一个 64 位浮点数.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static double readDouble(IDataInput input)
	{
		return input.readDouble();
	}
	
	/**
	 * 读取一个字符串.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 * @throws UnsupportedEncodingException 无法解码字符串时抛出.
	 */
	public static String readString(IDataInput input) throws UnsupportedEncodingException
	{
		return input.readUTF();
	}
	
	/**
	 * 读取一个字节数组.
	 * @param input 输入流对象.
	 * @return 对应的数据.
	 */
	public static byte[] readBytes(IDataInput input)
	{
		int len = (int) input.readUnsignedInt();
		byte[] bytes = new byte[len];
		input.readBytes(bytes, 0, len);
		return bytes;
	}
	
	/**
	 * 读取一个自定义数据.
	 * @param input 输入流对象.
	 * @param structClass 自定义数据类.
	 * @return 自定义数据.
	 * @throws Exception 会抛出的异常.
	 */
	public static AbstractStruct readStruct(IDataInput input, Class structClass) throws Exception
	{
		AbstractStruct target = (AbstractStruct) structClass.newInstance();
		target.readExternal(input);
		return target;
	}
}
