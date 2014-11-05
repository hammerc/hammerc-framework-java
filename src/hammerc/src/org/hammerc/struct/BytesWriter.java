// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.struct;

import org.hammerc.io.IDataOutput;

import java.math.BigInteger;

/**
 * 提供写入数据到字节流中的功能.
 * @author wizardc
 */
public class BytesWriter
{
	/**
	 * 写入一个布尔值.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeBoolean(IDataOutput output, boolean value)
	{
		output.writeByte(value ? (byte) 1 : (byte) 0);
	}
	
	/**
	 * 写入一个带符号 8 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeByte(IDataOutput output, byte value)
	{
		output.writeByte(value);
	}
	
	/**
	 * 写入一个无符号 8 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeUByte(IDataOutput output, short value)
	{
		output.writeUnsignedByte(value);
	}
	
	/**
	 * 写入一个带符号 16 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeShort(IDataOutput output, short value)
	{
		output.writeShort(value);
	}
	
	/**
	 * 写入一个无符号 16 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeUShort(IDataOutput output, int value)
	{
		output.writeUnsignedShort(value);
	}
	
	/**
	 * 写入一个带符号 32 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeInt(IDataOutput output, int value)
	{
		output.writeInt(value);
	}
	
	/**
	 * 写入一个无符号 32 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeUInt(IDataOutput output, long value)
	{
		output.writeUnsignedInt(value);
	}
	
	/**
	 * 写入一个带符号 64 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeLong(IDataOutput output, long value)
	{
		output.writeLong(value);
	}
	
	/**
	 * 写入一个无符号 64 位数字.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeULong(IDataOutput output, BigInteger value)
	{
		output.writeUnsignedLong(value);
	}
	
	/**
	 * 写入一个 32 位浮点数.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeFloat(IDataOutput output, float value)
	{
		output.writeFloat(value);
	}
	
	/**
	 * 写入一个 64 位浮点数.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeDouble(IDataOutput output, double value)
	{
		output.writeDouble(value);
	}
	
	/**
	 * 写入一个字符串.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeString(IDataOutput output, String value)
	{
		output.writeUTF(value);
	}
	
	/**
	 * 写入一个字节数组.
	 * @param output 输出流对象.
	 * @param value 要写入的数据.
	 */
	public static void writeBytes(IDataOutput output, byte[] value)
	{
		output.writeUnsignedInt(value.length);
		output.writeBytes(value);
	}
	
	/**
	 * 写入一个自定义数据.
	 * @param output 输出流对象.
	 * @param value 自定义数据.
	 * @throws Exception 会抛出的异常.
	 */
	public static void writeStruct(IDataOutput output, AbstractStruct value) throws Exception
	{
		value.writeExternal(output);
	}
}
