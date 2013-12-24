package org.hammerc.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 提供 ZLib 的压缩及解压方法.
 * @author wizardc
 */
public class ZLibUtil
{
	/**
	 * 使用 ZLib 压缩数据.
	 * @param data 待压缩数据.
	 * @return byte[] 压缩后的数据.
	 */
	public static byte[] compress(byte[] data)
	{
		byte[] output = new byte[0];
		Deflater compresser = new Deflater();
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try
		{
			byte[] buf = new byte[1024];
			while(!compresser.finished())
			{
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		}
		catch(Exception exception)
		{
			output = data;
		}
		finally
		{
			try
			{
				bos.close();
			}
			catch(IOException exception)
			{
			}
		}
		compresser.end();
		return output;
	}
	
	/**
	 * 使用 ZLib 解压.
	 * @param data 待压缩的数据.
	 * @return byte[] 解压缩后的数据.
	 */
	public static byte[] decompress(byte[] data)
	{
		byte[] output = new byte[0];
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);
		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try
		{
			byte[] buf = new byte[1024];
			while(!decompresser.finished())
			{
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		}
		catch(Exception exception)
		{
			output = data;
		}
		finally
		{
			try
			{
				o.close();
			}
			catch(IOException exception)
			{
			}
		}
		decompresser.end();
		return output;
	}
}
