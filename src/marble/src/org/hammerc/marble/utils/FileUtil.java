/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.marble.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 文件处理工具类.
 * @author wizardc
 */
public class FileUtil
{
	/**
	 * 系统路径分隔符.
	 */
	public static final String SEPARATOR = File.separator;
	
	/**
	 * 系统换行符.
	 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");
	
	/**
	 * 保存数据到指定文件.
	 * @param path 文件完整路径名.
	 * @param data 要保存的数据.
	 * @return 是否保存成功.
	 */
	public static boolean saveAsBytes(String path, byte[] data)
	{
		createDirectoty(path);
		try
		{
			FileOutputStream outputStream = new FileOutputStream(path);
			outputStream.write(data);
			outputStream.close();
		}
		catch(IOException exception)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 保存数据到指定文件.
	 * @param path 文件完整路径名.
	 * @param data 要保存的数据.
	 * @return 是否保存成功.
	 */
	public static boolean saveAsString(String path, String data)
	{
		return saveAsString(path, data, "UTF8");
	}
	
	/**
	 * 保存数据到指定文件.
	 * @param path 文件完整路径名.
	 * @param data 要保存的数据.
	 * @param charsetName 编码格式.
	 * @return 是否保存成功.
	 */
	public static boolean saveAsString(String path, String data, String charsetName)
	{
		try
		{
			byte[] bytes = data.getBytes(charsetName);
			return saveAsBytes(path, bytes);
		}
		catch(IOException exception)
		{
			return false;
		}
	}
	
	/**
	 * 打开文件的简便方法.
	 * @param path 文件完整路径名.
	 * @return 文件内容.
	 * @throws IOException 读写异常时抛出.
	 */
	public static byte[] readAsBytes(String path) throws IOException
	{
		File file = new File(path);
		Long length = file.length();
		byte[] bytes = new byte[length.intValue()];
		FileInputStream inputStream = new FileInputStream(file);
		inputStream.read(bytes);
		inputStream.close();
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
	
	/**
	 * 移动文件或目录.
	 * @param source 文件源路径.
	 * @param dest 文件要移动到的目标路径.
	 * @param overwrite 是否覆盖同名文件.
	 * @return 是否移动成功.
	 * @throws IOException 读写异常时抛出.
	 */
	public static boolean moveTo(String source, String dest, boolean overwrite) throws IOException
	{
		if(copyTo(source, dest, overwrite))
		{
			deletePath(source);
			return true;
		}
		return false;
	}
	
	/**
	 * 复制文件或目录.
	 * @param source 文件源路径.
	 * @param dest 文件要移动到的目标路径.
	 * @param overwrite 是否覆盖同名文件.
	 * @return 是否复制成功.
	 * @throws IOException 读写异常时抛出.
	 */
	public static boolean copyTo(String source, String dest, boolean overwrite) throws IOException
	{
		if(source.equals(dest))
		{
			return true;
		}
		File sourceFile = new File(source);
		File destFile = new File(dest);
		if(!sourceFile.exists())
		{
			return false;
		}
		if(destFile.exists() && !overwrite)
		{
			return false;
		}
		byte[] content = readAsBytes(source);
		saveAsBytes(dest, content);
		return true;
	}
	
	/**
	 * 删除文件或目录.
	 * @param path 要删除的文件源路径.
	 * @return 是否删除成功.
	 */
	public static boolean deletePath(String path)
	{
		File file = new File(path);
		if(file.isDirectory())
		{
			for(String item : file.list())
			{
				if(!deletePath(file.getAbsolutePath() + File.separator + item))
				{
					return false;
				}
			}
		}
		return file.delete();
	}
	
	/**
	 * 获取文件的文件夹路径.
	 * @param path 指定文件.
	 * @return 文件夹路径.
	 */
	public static String getDirectory(String path)
	{
		int separatePos = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
		return separatePos == -1 ? null : path.substring(0, separatePos);
	}
	
	/**
	 * 获取路径的文件名或文件夹名.
	 * @param path 要处理的地址.
	 * @return 对应的文件名或文件夹名.
	 */
	public static String getFileName(String path)
	{
		int separatePos = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
		return separatePos == -1 ? null : path.substring(separatePos + 1, path.length());
	}
	
	/**
	 * 获取文件扩展名.
	 * @param fileName 文件名.
	 * @return 文件扩展名.
	 */
	public static String getFileExtension(String fileName)
	{
		if(fileName == null || fileName.lastIndexOf(".") == -1 || fileName.lastIndexOf(".") == fileName.length() - 1)
		{
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * 获取指定目录下的所有文件列表.
	 * @param path 指定目录.
	 * @param recursive 是否包含子目录.
	 * @return 指定目录下的所有文件列表.
	 */
	public static List<File> getFileList(String path, boolean recursive)
	{
		return getFileList(path, "(.*)", recursive);
	}
	
	/**
	 * 获取指定目录下的所有文件列表.
	 * @param path 指定目录.
	 * @param regex 文件名过滤表达式.
	 * @param recursive 是否包含子目录.
	 * @return 指定目录下的所有文件列表.
	 */
	public static List<File> getFileList(String path, String regex, boolean recursive)
	{
		File rootFolder = new File(path);
		Pattern pattern = Pattern.compile(regex);
		return getFiles(rootFolder, pattern, recursive);
	}
	
	private static List<File> getFiles(File rootFolder, Pattern regexPattern, boolean recursive)
	{
		List<File> result = new ArrayList<File>();
		File[] files = rootFolder.listFiles();
		if(files != null)
		{
			for(File file : files)
			{
				if(file.isDirectory() && recursive)
				{
					result.addAll(getFiles(file, regexPattern, recursive));
				}
				else
				{
					if(regexPattern.matcher(file.getAbsolutePath()).matches())
					{
						result.add(file);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 指定路径的文件或文件夹是否存在.
	 * @param path 指定路径的文件或文件夹.
	 * @return 指定的文件或文件夹是否存在.
	 */
	public static boolean exists(String path)
	{
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * 创建指定的文件夹.
	 * @param path 文件夹路径.
	 */
	public static void createDirectoty(String path)
	{
		File file = new File(path);
		File directoty = file.isDirectory() ? file : file.getParentFile();
		if(!directoty.exists())
		{
			directoty.mkdirs();
		}
	}
	
	/**
	 * 格式化路径为当前系统可使用的路径, 去掉末尾的路径分隔符.
	 * @param path 带格式化的路径.
	 * @return 格式化的路径.
	 */
	public static String formatPath(String path)
	{
		String separator = SEPARATOR;
		if(separator.equals("\\"))
		{
			separator = "\\\\";
		}
		path = path.replaceAll("\\\\", separator);
		path = path.replaceAll("/", separator);
		int index = path.lastIndexOf(SEPARATOR);
		if(index == path.length() - 1)
		{
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
	
	/**
	 * 统一换行符为系统默认的换行符.
	 * @param source 带处理文本.
	 * @return 处理后的文本.
	 */
	public static String unifyEnter(String source)
	{
		source = source.replaceAll("\\r\\n", "\r");
		source = source.replaceAll("\\n", "\r");
		source = source.replaceAll("\\r", LINE_SEPARATOR);
		return source;
	}
}
