// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.io;

import java.math.BigInteger;
import java.nio.ByteOrder;

/**
 * 定义了数据输出类型.
 * @author wizardc
 */
public interface IDataOutput
{
	/**
	 * 设置字节顺序.
	 * @param order 字节顺序.
	 */
	public void endian(ByteOrder order);
	
	/**
	 * 获取字节顺序.
	 * @return 字节顺序.
	 */
	public ByteOrder endian();
	
	/**
	 * 写入布尔值.
	 * @param value 布尔值.
	 */
	public void writeBoolean(boolean value);
	
	/**
	 * 写入带符号字节.
	 * @param value 带符号字节.
	 */
	public void writeByte(byte value);
	
	/**
	 * 写入无符号字节.
	 * @param value 无符号字节.
	 */
	public void writeUnsignedByte(short value);
	
	/**
	 * 写入带符号短整型.
	 * @param value 带符号短整型.
	 */
	public void writeShort(short value);
	
	/**
	 * 写入无符号短整型.
	 * @param value 无符号短整型.
	 */
	public void writeUnsignedShort(int value);
	
	/**
	 * 写入带符号整型.
	 * @param value 带符号整型.
	 */
	public void writeInt(int value);
	
	/**
	 * 写入无符号整型.
	 * @param value 无符号整型.
	 */
	public void writeUnsignedInt(long value);
	
	/**
	 * 写入带符号长整型.
	 * @param value 带符号长整型.
	 */
	public void writeLong(long value);
	
	/**
	 * 写入无符号长整型.
	 * @param value 无符号长整型.
	 */
	public void writeUnsignedLong(BigInteger value);
	
	/**
	 * 写入单精度浮点数.
	 * @param value 单精度浮点数.
	 */
	public void writeFloat(float value);
	
	/**
	 * 写入双精度浮点数.
	 * @param value 双精度浮点数.
	 */
	public void writeDouble(double value);
	
	/**
	 * 写入 UTF-8 字符串.
	 * @param value 字符串.
	 */
	public void writeUTF(String value);
	
	/**
	 * 写入不带长度标识 UTF-8 字符串.
	 * @param value 字符串.
	 */
	public void writeUTFBytes(String value);
	
	/**
	 * 写入指定编码的字符串.
	 * @param value 字符串.
	 * @param charSet 指定的编码字符集.
	 */
	public void writeMultiBytes(String value, String charSet);
	
	/**
	 * 写入指定的字节数组.
	 * @param bytes 字节数组.
	 */
	public void writeBytes(byte[] bytes);
	
	/**
	 * 写入指定的字节数组.
	 * @param bytes 字节数组.
	 * @param offset 偏移量.
	 * @param length 指定的长度.
	 */
	public void writeBytes(byte[] bytes, int offset, int length);
}
