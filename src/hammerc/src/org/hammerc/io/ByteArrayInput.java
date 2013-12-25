/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.io;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.hammerc.utils.ByteUtil;
import org.hammerc.utils.UnsignedUtil;

/**
 * 数据输入类.
 * @author wizardc
 */
public class ByteArrayInput implements IDataInput
{
	/**
	 * 字节缓冲对象.
	 */
	protected ByteBuffer _byteBuffer;
	
	/**
	 * 构造函数.
	 * @param bytes 字节数组.
	 */
	public ByteArrayInput(byte[] bytes)
	{
		_byteBuffer = ByteBuffer.wrap(bytes);
	}
	
	/**
	 * 构造函数.
	 * @param byteBuffer 字节缓冲对象.
	 */
	public ByteArrayInput(ByteBuffer byteBuffer)
	{
		_byteBuffer = byteBuffer;
	}
	
	@Override
	public int bytesAvailable()
	{
		return _byteBuffer.remaining();
	}
	
	@Override
	public void endian(ByteOrder order)
	{
		_byteBuffer.order(order);
	}
	
	@Override
	public ByteOrder endian()
	{
		return _byteBuffer.order();
	}
	
	/**
	 * 获取字节缓冲对象.
	 * @return 字节缓冲对象.
	 */
	public ByteBuffer byteBuffer()
	{
		return _byteBuffer;
	}
	
	@Override
	public boolean readBoolean()
	{
		return _byteBuffer.get() != 0;
	}
	
	@Override
	public byte readByte()
	{
		return _byteBuffer.get();
	}
	
	@Override
	public short readUnsignedByte()
	{
		byte b = _byteBuffer.get();
		return UnsignedUtil.readUnsignedByte(b);
	}
	
	@Override
	public short readShort()
	{
		return _byteBuffer.getShort();
	}
	
	@Override
	public int readUnsignedShort()
	{
		byte[] bytes = null;
		_byteBuffer.get(bytes, 0, 2);
		if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
		{
			bytes = ByteUtil.reverse(bytes);
		}
		return UnsignedUtil.readUnsignedShort(bytes);
	}
	
	@Override
	public int readInt()
	{
		return _byteBuffer.getInt();
	}
	
	@Override
	public long readUnsignedInt()
	{
		byte[] bytes = null;
		_byteBuffer.get(bytes, 0, 4);
		if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
		{
			bytes = ByteUtil.reverse(bytes);
		}
		return UnsignedUtil.readUnsignedInt(bytes);
	}
	
	@Override
	public long readLong()
	{
		return _byteBuffer.getLong();
	}
	
	@Override
	public BigInteger readUnsignedLong()
	{
		byte[] bytes = null;
		_byteBuffer.get(bytes, 0, 8);
		if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
		{
			bytes = ByteUtil.reverse(bytes);
		}
		return UnsignedUtil.readUnsignedLong(bytes);
	}
	
	@Override
	public float readFloat()
	{
		return _byteBuffer.getFloat();
	}
	
	@Override
	public double readDouble()
	{
		return _byteBuffer.getDouble();
	}
	
	@Override
	public String readUTF() throws UnsupportedEncodingException
	{
		int length = this.readUnsignedShort();
		return readUTFBytes(length);
	}
	
	@Override
	public String readUTFBytes(int length) throws UnsupportedEncodingException
	{
		byte[] bytes = null;
		_byteBuffer.get(bytes, 0, length);
		return new String(bytes, "UTF-8");
	}
	
	@Override
	public String readMultiBytes(int length, String charSet) throws UnsupportedEncodingException
	{
		byte[] bytes = null;
		_byteBuffer.get(bytes, 0, length);
		return new String(bytes, charSet);
	}
	
	@Override
	public void readBytes(byte[] bytes)
	{
		_byteBuffer.get(bytes);
	}
	
	@Override
	public void readBytes(byte[] bytes, int offset, int length)
	{
		_byteBuffer.get(bytes, offset, length);
	}
}
