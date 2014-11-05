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
 * AbstractIntervalTask 类抽象出了间隔任务的主要属性.
 * @author wizardc
 */
public abstract class AbstractIntervalTask implements IIntervalTask
{
	//记录该任务的第一次运行时间
	private long _delayTime = 0;
	//记录该任务在第一次运行结束后以后再次运行的间隔
	private long _intervalTime = 0;
	//记录当前运行的次数
	private int _nowRepeat = 0;
	//记录该任务在第一次运行之后再次运行的次数, 负数表示无限循环
	private int _repeatCount = -1;
	
	/**
	 * 创建一个 AbstractIntervalTask 对象, 该对象将无限循环执行.
	 * @param delayTime 指定该任务的第一次运行时间.
	 * @param intervalTime 指定该任务在第一次运行结束后以后再次运行的间隔.
	 */
	public AbstractIntervalTask(long delayTime, long intervalTime)
	{
		_delayTime = delayTime;
		_intervalTime = intervalTime;
	}
	
	/**
	 * 创建一个 AbstractIntervalTask 对象.
	 * @param delayTime 指定该任务的第一次运行时间.
	 * @param intervalTime 指定该任务在第一次运行结束后以后再次运行的间隔.
	 * @param repeatCount 该任务在第一次运行之后再次运行的次数, 负数表示无限循环.
	 */
	public AbstractIntervalTask(long delayTime, long intervalTime, int repeatCount)
	{
		_delayTime = delayTime;
		_intervalTime = intervalTime;
		if(repeatCount > -1)
		{
			_repeatCount = repeatCount;
		}
	}
	
	@Override
	public long getDelayTime()
	{
		return _delayTime;
	}
	
	@Override
	public long getIntervalTime()
	{
		return _intervalTime;
	}
	
	@Override
	public int getAndIncrementRepeat()
	{
		return _nowRepeat++;
	}
	
	@Override
	public int getRepeatCount()
	{
		return _repeatCount;
	}
}
