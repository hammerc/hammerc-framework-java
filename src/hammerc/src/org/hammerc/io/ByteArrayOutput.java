/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.io;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

import org.hammerc.utils.ByteUtil;
import org.hammerc.utils.UnsignedUtil;

/**
 * 数据输出类.
 * @author wizardc
 */
public class ByteArrayOutput implements IDataOutput
{
	/**
	 * 数据记录列表.
	 */
	protected ArrayList<TypeInfo> _arrayList;
	
	/**
	 * 字节顺序.
	 */
	protected ByteOrder _order;
	
	/**
	 * 构造函数.
	 */
	public ByteArrayOutput()
	{
		_arrayList = new ArrayList<TypeInfo>();
		_order = ByteOrder.BIG_ENDIAN;
	}
	
	@Override
	public void endian(ByteOrder order)
	{
		_order = order;
	}
	
	@Override
	public ByteOrder endian()
	{
		return _order;
	}
	
	/**
	 * 获取数据对应的字节数组.
	 * @return 字节数组.
	 * @throws UnsupportedEncodingException 无法编码字符串时抛出.
	 */
	public byte[] bytes() throws UnsupportedEncodingException
	{
		if(_arrayList.size() == 0)
		{
			return null;
		}
		//记录字符串转换后的字节避免转换两次
		ArrayList<byte[]> bytesList = new ArrayList<byte[]>();
		//先计算出需要的字节的总长度
		int size = 0;
		byte[] bytes = null;
		Iterator<TypeInfo> it = _arrayList.iterator();
		while(it.hasNext())
		{
			TypeInfo ti = it.next();
			switch(ti.type)
			{
				case TypeInfo.BOOLEAN:
				case TypeInfo.BYTE:
				case TypeInfo.UNSIGNED_BYTE:
					++size;
					break;
				case TypeInfo.SHORT:
				case TypeInfo.UNSIGNED_SHORT:
					size += 2;
					break;
				case TypeInfo.INT:
				case TypeInfo.UNSIGNED_INT:
				case TypeInfo.FLOAT:
					size += 4;
					break;
				case TypeInfo.LONG:
				case TypeInfo.UNSIGNED_LONG:
				case TypeInfo.DOUBLE:
					size += 8;
					break;
				case TypeInfo.UTF:
					size += 2;
					bytes = ((String) ti.value).getBytes("UTF-8");
					bytesList.add(bytes);
					size += bytes.length;
					break;
				case TypeInfo.UTF_BYTES:
					bytes = ((String) ti.value).getBytes("UTF-8");
					bytesList.add(bytes);
					size += bytes.length;
					break;
				case TypeInfo.MULTI_BYTES:
					bytes = ((String) ti.value).getBytes(ti.charSet);
					bytesList.add(bytes);
					size += bytes.length;
					break;
				case TypeInfo.BYTES:
					bytes = (byte[]) ti.value;
					bytesList.add(bytes);
					size += bytes.length;
					break;
			}
		}
		//将数据写入字节流中
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		byteBuffer.order(_order);
		byte[] numBytes = null;
		int i = 0;
		it = _arrayList.iterator();
		while(it.hasNext())
		{
			TypeInfo ti = it.next();
			switch(ti.type)
			{
				case TypeInfo.BOOLEAN:
					if(((Boolean) ti.value).booleanValue())
					{
						byteBuffer.put((byte) 1);
					}
					else
					{
						byteBuffer.put((byte) 0);
					}
					break;
				case TypeInfo.BYTE:
					byteBuffer.put(((Byte) ti.value).byteValue());
					break;
				case TypeInfo.UNSIGNED_BYTE:
					byteBuffer.put(UnsignedUtil.writeUnsignedByte(((Short) ti.value).shortValue()));
					break;
				case TypeInfo.SHORT:
					byteBuffer.putShort(((Short) ti.value).shortValue());
					break;
				case TypeInfo.UNSIGNED_SHORT:
					numBytes = UnsignedUtil.writeUnsignedShort(((Integer) ti.value).intValue());
					if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
					{
						numBytes = ByteUtil.reverse(numBytes);
					}
					byteBuffer.put(numBytes);
					break;
				case TypeInfo.INT:
					byteBuffer.putInt(((Integer) ti.value).intValue());
					break;
				case TypeInfo.UNSIGNED_INT:
					numBytes = UnsignedUtil.writeUnsignedInt(((Long) ti.value).longValue());
					if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
					{
						numBytes = ByteUtil.reverse(numBytes);
					}
					byteBuffer.put(numBytes);
					break;
				case TypeInfo.LONG:
					byteBuffer.putLong(((Long) ti.value).longValue());
					break;
				case TypeInfo.UNSIGNED_LONG:
					numBytes = UnsignedUtil.writeUnsignedLong((BigInteger) ti.value);
					if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
					{
						numBytes = ByteUtil.reverse(numBytes);
					}
					byteBuffer.put(numBytes);
					break;
				case TypeInfo.FLOAT:
					byteBuffer.putFloat(((Float) ti.value).floatValue());
					break;
				case TypeInfo.DOUBLE:
					byteBuffer.putDouble(((Double) ti.value).doubleValue());
					break;
				case TypeInfo.UTF:
					bytes = bytesList.get(i++);
					numBytes = UnsignedUtil.writeUnsignedShort(bytes.length);
					if(this.endian().equals(ByteOrder.LITTLE_ENDIAN))
					{
						numBytes = ByteUtil.reverse(numBytes);
					}
					byteBuffer.put(numBytes);
					byteBuffer.put(bytes);
					break;
				case TypeInfo.UTF_BYTES:
					bytes = bytesList.get(i++);
					byteBuffer.put(bytes);
					break;
				case TypeInfo.MULTI_BYTES:
					bytes = bytesList.get(i++);
					byteBuffer.put(bytes);
					break;
				case TypeInfo.BYTES:
					bytes = bytesList.get(i++);
					byteBuffer.put(bytes);
					break;
			}
		}
		return byteBuffer.array();
	}
	
	@Override
	public void writeBoolean(boolean value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.BOOLEAN, value));
	}
	
	@Override
	public void writeByte(byte value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.BYTE, value));
	}
	
	@Override
	public void writeUnsignedByte(short value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UNSIGNED_BYTE, value));
	}
	
	@Override
	public void writeShort(short value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.SHORT, value));
	}
	
	@Override
	public void writeUnsignedShort(int value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UNSIGNED_SHORT, value));
	}
	
	@Override
	public void writeInt(int value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.INT, value));
	}
	
	@Override
	public void writeUnsignedInt(long value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UNSIGNED_INT, value));
	}
	
	@Override
	public void writeLong(long value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.LONG, value));
	}
	
	@Override
	public void writeUnsignedLong(BigInteger value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UNSIGNED_LONG, value));
	}
	
	@Override
	public void writeFloat(float value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.FLOAT, value));
	}
	
	@Override
	public void writeDouble(double value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.DOUBLE, value));
	}
	
	@Override
	public void writeUTF(String value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UTF, value));
	}
	
	@Override
	public void writeUTFBytes(String value)
	{
		_arrayList.add(new TypeInfo(TypeInfo.UTF_BYTES, value));
	}
	
	@Override
	public void writeMultiBytes(String value, String charSet)
	{
		_arrayList.add(new TypeInfo(TypeInfo.MULTI_BYTES, value, charSet));
	}
	
	@Override
	public void writeBytes(byte[] bytes)
	{
		_arrayList.add(new TypeInfo(TypeInfo.BYTES, bytes));
	}
	
	@Override
	public void writeBytes(byte[] bytes, int offset, int length)
	{
		byte[] useBytes = new byte[length - offset];
		System.arraycopy(bytes, offset, useBytes, 0, useBytes.length);
		this.writeBytes(useBytes);
	}
	
	/**
	 * 清除数据.
	 */
	public void clear()
	{
		_arrayList.clear();
	}
}

class TypeInfo
{
	public static final int BOOLEAN = 0;
	public static final int BYTE = 1;
	public static final int UNSIGNED_BYTE = 2;
	public static final int SHORT = 3;
	public static final int UNSIGNED_SHORT = 4;
	public static final int INT = 5;
	public static final int UNSIGNED_INT = 6;
	public static final int LONG = 7;
	public static final int UNSIGNED_LONG = 8;
	public static final int FLOAT = 9;
	public static final int DOUBLE = 10;
	public static final int UTF = 11;
	public static final int UTF_BYTES = 12;
	public static final int MULTI_BYTES = 13;
	public static final int BYTES = 14;
	
	public int type;
	
	public Object value;
	
	public String charSet;
	
	public TypeInfo(int type, Object value)
	{
		this.type = type;
		this.value = value;
	}
	
	public TypeInfo(int type, Object value, String charSet)
	{
		this.type = type;
		this.value = value;
		this.charSet = charSet;
	}
}
