// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.scheduled;

/**
 * IIntervalTask 接口定义了间隔任务类的统一接口.
 * @author wizardc
 */
public interface IIntervalTask extends IScheduledTask
{
	/**
	 * 获取该任务第一次运行的时间戳.
	 * @return 该任务第一次运行的时间戳.
	 */
	public long getDelayTime();
	
	/**
	 * 获取该任务在第一次运行结束后以后再次运行的间隔.
	 * @return 该任务在第一次运行结束后以后再次运行的间隔.
	 */
	public long getIntervalTime();
	
	/**
	 * 获取当前运行了的次数后在自增 1.
	 * @return 当前运行了的次数.
	 */
	public int getAndIncrementRepeat();
	
	/**
	 * 获取重复运行的总次数.
	 * @return 重复运行的总次数.
	 */
	public int getRepeatCount();
}
