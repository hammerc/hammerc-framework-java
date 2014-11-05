// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.scheduled;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * TimeoutTaskManager 类用来管理所有的超时任务.
 * @author wizardc
 */
public class TimeoutTaskManager implements ITaskManager
{
	//计时器执行间隔, 单位毫秒
	private int _period = 50;
	//记录当前的时间索引, 即时间戳除以计时器执行间隔后得到的数字
	private long _currentIndex = 0;
	//记录所有超时任务的哈希表
	private Hashtable<Long, Vector<ITimeoutTask>> _tasks;
	
	/**
	 * 创建一个 TimeoutTaskManager 对象.
	 */
	public TimeoutTaskManager()
	{
		_tasks = new Hashtable<Long, Vector<ITimeoutTask>>();
	}
	
	@Override
	public void initialize(int period)
	{
		if(period > 0)
		{
			_period = period;
		}
		_currentIndex = System.currentTimeMillis() / _period;
	}
	
	/**
	 * 添加一个超时任务到该管理对象中.
	 * @param task 要添加的超时任务对象.
	 */
	public void addTimeoutTask(ITimeoutTask task)
	{
		boolean isTimeout = false;
		//获取超时任务的时间索引
		long index = (task.getExecutionTime() + _period - 1) / _period;
		synchronized(this)
		{
			//判断当前的时间是否已经达到超时任务的时间
			if(index < _currentIndex)
			{
				isTimeout = true;
			}
			else
			{
				//使用该超时任务的时间索引为键存储该任务
				if(!_tasks.containsKey(index))
				{
					_tasks.put(index, new Vector<ITimeoutTask>());
				}
				_tasks.get(index).addElement(task);
			}
		}
		//如果超时任务的时间已经达到则立即执行该任务
		if(isTimeout)
		{
			runTask(task);
		}
	}
	
	/**
	 * 移除一个超时任务.
	 * @param task 要被移除的超时任务对象.
	 */
	public void removeTimeoutTask(ITimeoutTask task)
	{
		//获取超时任务的时间索引
		long index = (task.getExecutionTime() + _period - 1) / _period;
		synchronized(this)
		{
			//该时间索引存在就移除该超时对象
			if(_tasks.containsKey(index))
			{
				_tasks.get(index).removeElement(task);
			}
		}
	}
	
	@Override
	public void run()
	{
		//记录上次的时间索引
		long index = _currentIndex;
		synchronized(this)
		{
			//获取当前的时间索引
			_currentIndex = System.currentTimeMillis() / _period + 1;
		}
		//遍历经过的所有时间索引, 如果有对应的超时任务在该时间点上则运行该任务
		for(; index < _currentIndex; index++)
		{
			runTasks(index);
		}
	}
	
	private void runTasks(long index)
	{
		Vector<ITimeoutTask> tasks = _tasks.remove(index);
		if(tasks != null)
		{
			Iterator<ITimeoutTask> task = tasks.iterator();
			while(task.hasNext())
			{
				runTask(task.next());
			}
		}
	}
	
	private void runTask(ITimeoutTask task)
	{
		try
		{
			task.run();
		}
		catch(Exception exception)
		{
		}
	}
	
	@Override
	public void clear()
	{
		_tasks.clear();
		_currentIndex = System.currentTimeMillis() / _period;
	}
}
