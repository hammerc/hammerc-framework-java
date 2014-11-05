// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.utils;

import java.math.BigInteger;

/**
 * Java 本身不支持无符号整型, 本工具类提供无符号整型和字节之间相互转换的功能方便与其它语音通讯.
 * <p>注: 字节顺序使用 Java 默认的 Big-endian.<p>
 * @author wizardc
 */
public class UnsignedUtil
{
	/**
	 * 读取无符号字节.
	 * @param b 对应的字节.
	 * @return 无符号整型.
	 */
	public static short readUnsignedByte(byte b)
	{
		return (short) (b & 0xff);
	}
	
	/**
	 * 读取无符号短整型.
	 * @param bytes 对应的字节.
	 * @return 无符号整型.
	 */
	public static int readUnsignedShort(byte[] bytes)
	{
		return ((bytes[0] << 8) & 0xff00) + (bytes[1] & 0xff);
	}
	
	/**
	 * 读取无符号整型.
	 * @param bytes 对应的字节.
	 * @return 无符号整型.
	 */
	public static long readUnsignedInt(byte[] bytes)
	{
		return ((long) ((bytes[0] << 24 & 0xff000000) + (bytes[1] << 16 & 0xff0000) + (bytes[2] << 8 & 0xff00) + (bytes[3] & 0xff))) & 0xffffffffL;
	}
	
	/**
	 * 读取无符号长整型.
	 * @param bytes 对应的字节.
	 * @return 无符号整型.
	 */
	public static BigInteger readUnsignedLong(byte[] bytes)
	{
		return new BigInteger(1, bytes);
	}
	
	/**
	 * 转换无符号整型为字节.
	 * @param value 无符号字节.
	 * @return 对应的字节.
	 */
	public static byte writeUnsignedByte(short value)
	{
		return (byte) value;
	}
	
	/**
	 * 转换无符号整型为字节.
	 * @param value 无符号短整型.
	 * @return 对应的字节.
	 */
	public static byte[] writeUnsignedShort(int value)
	{
		byte[] bytes = new byte[2];
		bytes[1] = (byte) (value & 0xff);
		bytes[0] = (byte) (value >> 8 & 0xff);
		return bytes;
	}
	
	/**
	 * 转换无符号整型为字节.
	 * @param value 无符号整型.
	 * @return 对应的字节.
	 */
	public static byte[] writeUnsignedInt(long value)
	{
		byte[] bytes = new byte[4];
		bytes[3] = (byte) (value & 0xff);
		bytes[2] = (byte) (value >> 8 & 0xff);
		bytes[1] = (byte) (value >> 16 & 0xff);
		bytes[0] = (byte) (value >> 24 & 0xff);
		return bytes;
	}
	
	/**
	 * 转换无符号整型为字节.
	 * @param value 无符号长整型.
	 * @return 对应的字节.
	 */
	public static byte[] writeUnsignedLong(BigInteger value)
	{
		byte[] bytes = new byte[8];
		BigInteger bigInt = new BigInteger("255");
		for(int i = 0; i < 8; i++)
		{
			bytes[i] = (value.shiftRight((7 - i) * 8).and(bigInt).byteValue());
		}
		return bytes;
	}
}
