/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.marble.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;

/**
 * 打包到 Jar 内部的文件获取类.
 * @author wizardc
 */
public class EmbedFileReader
{
	/**
	 * 打开文件的简便方法.
	 * @param path 文件完整路径名.
	 * @return 文件内容.
	 * @throws IOException 读写异常时抛出.
	 */
	public static byte[] readAsBytes(String path) throws IOException
	{
		InputStream inputStream = FileSystem.class.getClass().getResourceAsStream(path);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		byte[] bytes = new byte[dataInputStream.available()];
		dataInputStream.read(bytes);
		return bytes;
	}
	
	/**
	 * 打开文件的简便方法.
	 * @param path 文件完整路径名.
	 * @return 文件内容.
	 * @throws IOException 读写异常时抛出.
	 */
	public static String readAsString(String path) throws IOException
	{
		return readAsString(path, "UTF8");
	}
	
	/**
	 * 打开文件的简便方法.
	 * @param path 文件完整路径名.
	 * @param charsetName 编码格式.
	 * @return 文件内容.
	 * @throws IOException 读写异常时抛出.
	 */
	public static String readAsString(String path, String charsetName) throws IOException
	{
		byte[] bytes = readAsBytes(path);
		return new String(bytes, charsetName);
	}
}
