// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.utils;

/**
 * 字节操作工具类.
 * @author wizardc
 */
public class ByteUtil
{
	/**
	 * 逆转字节数组.
	 * @param bytes 字节数组.
	 * @return 逆转后的字节数组.
	 */
	public static byte[] reverse(byte[] bytes)
	{
		byte[] result = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++)
		{
			result[i] = bytes[bytes.length - 1 - i];
		}
		return result;
	}
}
