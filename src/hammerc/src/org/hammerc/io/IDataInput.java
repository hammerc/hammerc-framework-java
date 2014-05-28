/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.io;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteOrder;

/**
 * 定义了数据输入类型.
 * @author wizardc
 */
public interface IDataInput
{
	/**
	 * 获取剩余可用的字节.
	 * @return 剩余可用的字节.
	 */
	public int bytesAvailable();
	
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
	 * 设置指针位置.
	 * @param newPosition 新的指针位置.
	 */
	public void position(int newPosition);
	
	/**
	 * 获取指针位置.
	 * @return 指针位置.
	 */
	public int position();
	
	/**
	 * 读取布尔值.
	 * @return 布尔值.
	 */
	public boolean readBoolean();
	
	/**
	 * 读取带符号字节.
	 * @return 带符号字节.
	 */
	public byte readByte();
	
	/**
	 * 读取无符号字节.
	 * @return 无符号字节.
	 */
	public short readUnsignedByte();
	
	/**
	 * 读取带符号短整型.
	 * @return 带符号短整型.
	 */
	public short readShort();
	
	/**
	 * 读取无符号短整型.
	 * @return 无符短号整型.
	 */
	public int readUnsignedShort();
	
	/**
	 * 读取带符号整型.
	 * @return 带符号整型.
	 */
	public int readInt();
	
	/**
	 * 读取无符号整型.
	 * @return 无符号整型.
	 */
	public long readUnsignedInt();
	
	/**
	 * 读取带符号长整型.
	 * @return 带符号长整型.
	 */
	public long readLong();
	
	/**
	 * 读取无符号长整型.
	 * @return 无符号长整型.
	 */
	public BigInteger readUnsignedLong();
	
	/**
	 * 读取单精度浮点数.
	 * @return 单精度浮点数.
	 */
	public float readFloat();
	
	/**
	 * 读取双精度浮点数.
	 * @return 双精度浮点数.
	 */
	public double readDouble();
	
	/**
	 * 读取 UTF-8 字符串.
	 * @return UTF-8 字符串.
	 * @throws UnsupportedEncodingException 无法解码字符串时抛出.
	 */
	public String readUTF() throws UnsupportedEncodingException;
	
	/**
	 * 读取指定长度的 UTF-8 字符串.
	 * @param length 要读取的长度.
	 * @return 字符串.
	 * @throws UnsupportedEncodingException 无法解码字符串时抛出.
	 */
	public String readUTFBytes(int length) throws UnsupportedEncodingException;
	
	/**
	 * 读取指定长度的指定编码的字符串.
	 * @param length 要读取的长度.
	 * @param charSet 指定的编码字符集.
	 * @return 字符串.
	 * @throws UnsupportedEncodingException 无法解码字符串时抛出.
	 */
	public String readMultiBytes(int length, String charSet) throws UnsupportedEncodingException;
	
	/**
	 * 读取剩余的所有字节.
	 * @param bytes 要放入的字节数组.
	 */
	public void readBytes(byte[] bytes);
	
	/**
	 * 读取指定长度的字节.
	 * @param bytes 要放入的字节数组.
	 * @param offset 偏移量.
	 * @param length 指定的长度.
	 */
	public void readBytes(byte[] bytes, int offset, int length);
}
