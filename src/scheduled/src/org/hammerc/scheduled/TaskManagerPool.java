/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.scheduled;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * TaskManagerPool 类为计划任务管理提供了对象池功能.
 * @author wizardc
 */
public class TaskManagerPool
{
	//计时器执行间隔, 单位毫秒
	private int _period = 50;
	//用于记录计划任务的对象池
	private Hashtable<String, ITaskManager> _taskManagerPool;
	
	/**
	 * 创建一个 TaskManagerPool 对象.
	 */
	public TaskManagerPool()
	{
		_taskManagerPool = new Hashtable<String, ITaskManager>();
	}
	
	/**
	 * 初始化本对象.
	 * @param period 设置计时器间隔时间, 单位毫秒.
	 */
	public void initialize(int period)
	{
		if(period > 0)
		{
			_period = period;
		}
		Collection<ITaskManager> values = _taskManagerPool.values();
		Iterator<ITaskManager> value = values.iterator();
		while(value.hasNext())
		{
			value.next().initialize(_period);
		}
	}
	
	/**
	 * 添加一个任务管理对象.
	 * @param name 该任务管理对象的名称.
	 * @param taskManager 要添加的任务管理对象.
	 * @return 添加的任务管理对象.
	 * @throws RuntimeException 当指定的名称已经被使用时抛出该异常.
	 */
	public ITaskManager addTaskManager(String name, ITaskManager taskManager) throws RuntimeException
	{
		if(_taskManagerPool.containsKey(name))
		{
			throw new RuntimeException("The name \"" + name + "\" already exists!");
		}
		taskManager.initialize(_period);
		return _taskManagerPool.put(name, taskManager);
	}
	
	/**
	 * 获取一个任务管理对象.
	 * @param name 该任务管理对象的名称.
	 * @return 指定的任务管理对象.
	 */
	public ITaskManager getTaskManager(String name)
	{
		return _taskManagerPool.get(name);
	}
	
	/**
	 * 移除一个任务管理对象.
	 * @param name 要移除的任务管理对象名称.
	 * @return 移除的任务管理对象.
	 */
	public ITaskManager removeTaskManager(String name)
	{
		return _taskManagerPool.remove(name);
	}
	
	/**
	 * 每次当计时器的事件到达时都会调用该方法.
	 */
	public void run()
	{
		Collection<ITaskManager> values = _taskManagerPool.values();
		Iterator<ITaskManager> value = values.iterator();
		while(value.hasNext())
		{
			value.next().run();
		}
	}
	
	/**
	 * 清除当前的所有任务.
	 */
	public void cleanup()
	{
		Collection<ITaskManager> values = _taskManagerPool.values();
		Iterator<ITaskManager> value = values.iterator();
		while(value.hasNext())
		{
			value.next().clear();
		}
	}
}
