/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.scheduled;

/**
 * ITaskManager 接口定义了任务管理类的统一接口.
 * @author wizardc
 */
public interface ITaskManager
{
	/**
	 * 初始化本对象.
	 * @param period 设置计时器间隔时间, 单位毫秒.
	 */
	public void initialize(int period);
	
	/**
	 * 每当有计时器的时间到达事件时运行.
	 */
	public void run();
	
	/**
	 * 清除任务.
	 */
	public void clear();
}
