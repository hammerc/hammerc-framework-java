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
 * IScheduledTask 接口定义了计划任务的基本接口.
 * @author wizardc
 */
public interface IScheduledTask
{
	/**
	 * 当计划任务的时间到达时会触发该方法.
	 */
	public void run();
}
