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
 * AbstractTimeoutTask 类抽象出了超时任务的主要属性.
 * @author wizardc
 */
public abstract class AbstractTimeoutTask implements ITimeoutTask
{
	//记录该任务的运行时间
	private long _excutionTime = 0;
	
	/**
	 * 创建一个 AbstractTimeoutTask 对象.
	 * @param excutionTime 指定该任务的运行时间.
	 */
	public AbstractTimeoutTask(long excutionTime)
	{
		_excutionTime = excutionTime;
	}
	
	@Override
	public long getExecutionTime()
	{
		return _excutionTime;
	}
}
