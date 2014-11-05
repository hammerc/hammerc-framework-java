// =================================================================================================
//
//	Hammerc Framework
//	Copyright 2013 hammerc.org All Rights Reserved.
//
//	See LICENSE for full license information.
//
// =================================================================================================

package org.hammerc.scheduled;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * IntervalTaskManager 类用来管理所有的间隔任务.
 * @author wizardc
 */
public class IntervalTaskManager implements ITaskManager
{
	//记录是否调用了初始化方法进行了初始化
	private boolean _initialized = false;
	//计时器执行间隔, 单位毫秒
	private int _period = 50;
	//记录上一次的时间索引
	private long _lastIndex = 0;
	//记录所有的间隔任务
	private Hashtable<Integer, IIntervalTask> _tasks;
	//记录每个时间索引对应的所有间隔任务
	private Hashtable<Long, HashSet<Integer>> _runTasks;
	
	/**
	 * 创建一个 IntervalTaskManager 对象.
	 */
	public IntervalTaskManager()
	{
		_tasks = new Hashtable<Integer, IIntervalTask>();
		_runTasks = new Hashtable<Long, HashSet<Integer>>();
	}
	
	@Override
	public void initialize(int period)
	{
		if(period > 0)
		{
			_period = period;
		}
		_lastIndex = System.currentTimeMillis() / _period;
		//如果在没有进行初始化之前就添加了间隔任务则在这里将之前添加的任务添加到运行列表中
		synchronized(this)
		{
			Enumeration<Integer> keys = _tasks.keys();
			while(keys.hasMoreElements())
			{
				Integer id = keys.nextElement();
				IIntervalTask task = _tasks.get(id);
				addToRunTasks(id, task);
			}
			_initialized = true;
		}
	}
	
	/**
	 * 添加一个间隔任务.
	 * @param id 指定该任务的唯一标示.
	 * @param task 要添加的间隔任务.
	 * @throws RuntimeException 当指定的 id 已经被使用或添加的任务的间隔时间小于计时器的执行间隔时抛出该异常.
	 */
	public void addIntervalTask(int id, IIntervalTask task) throws RuntimeException
	{
		if(_tasks.containsKey(id))
		{
			throw new RuntimeException("The id \"" + id + "\" already exists!");
		}
		//添加的任务的间隔时间不能小于计时器的执行间隔
		if(task.getIntervalTime() < _period)
		{
			throw new RuntimeException("Interval of time between tasks can not be less than the period time!");
		}
		synchronized(this)
		{
			//记录该任务
			_tasks.put(id, task);
			//初始化后添加该任务到运行列表中
			if(_initialized)
			{
				addToRunTasks(id, task);
			}
		}
	}
	
	private void addToRunTasks(int id, IIntervalTask task)
	{
		//获取该任务的第一次运行时间
		long index = task.getDelayTime() / _period;
		if(index <= _lastIndex)
		{
			index = _lastIndex + 1;
		}
		addTasksIndex(id, index);
	}
	
	private void addTasksIndex(int id, long index)
	{
		//将对应的任务的 id 添加到其下一次运行的时间索引中
		HashSet<Integer> taskIds = _runTasks.get(index);
		if(taskIds == null)
		{
			taskIds = new HashSet<Integer>();
			_runTasks.put(index, taskIds);
		}
		taskIds.add(id);
	}
	
	/**
	 * 移除一个间隔任务.
	 * @param id 要被移除的间隔任务对象.
	 */
	public void removeIntervalTask(int id)
	{
		_tasks.remove(id);
	}
	
	@Override
	public void run()
	{
		long index = _lastIndex + 1;
		long currentIndex = System.currentTimeMillis() / _period;
		for(; index <= currentIndex; index++)
		{
			synchronized(this)
			{
				_lastIndex = index;
			}
			runAllTask(index);
		}
	}
	
	private void runAllTask(long index)
	{
		//取出指定时间索引上的所有任务
		HashSet<Integer> taskIds = _runTasks.get(index);
		if(taskIds != null)
		{
			Iterator<Integer> task = taskIds.iterator();
			while(task.hasNext())
			{
				runIntervalTask(task.next(), index);
			}
			//移除处理后的所有任务
			taskIds.clear();
			_runTasks.remove(index);
		}
	}
	
	private void runIntervalTask(int id, long index)
	{
		IIntervalTask task = _tasks.get(id);
		//记录该任务下一次是否还要继续执行
		boolean repeat = true;
		if(task != null)
		{
			try
			{
				if(task.getRepeatCount() == -1)
				{
					task.run();
				}
				else
				{
					if(task.getAndIncrementRepeat() > task.getRepeatCount())
					{
						repeat = false;
					}
					else
					{
						task.run();
					}
				}
			}
			catch(Exception exception)
			{
			}
			//记录任务下一次运行的时间并再次加入运行列表
			if(repeat)
			{
				addTasksIndex(id, index + (task.getIntervalTime() / _period));
			}
		}
	}
	
	@Override
	public void clear()
	{
		_tasks.clear();
		_runTasks.clear();
		_lastIndex = System.currentTimeMillis() / _period;
	}
}
