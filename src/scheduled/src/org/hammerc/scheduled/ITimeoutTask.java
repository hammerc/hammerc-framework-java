/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.scheduled;

/**
 * ITimeoutTask 接口定义了超时任务类的统一接口.
 * @author wizardc
 */
public interface ITimeoutTask extends IScheduledTask
{
	/**
	 * 获取该任务运行的时间戳.
	 * @return 该任务运行的时间戳.
	 */
	public long getExecutionTime();
}
