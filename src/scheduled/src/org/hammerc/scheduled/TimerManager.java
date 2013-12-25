/**
 * Copyright (c) 2011-2014 hammerc.org
 * See LICENSE.txt for full license information.
 */
package org.hammerc.scheduled;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimerManager 类为各种计划任务管理类提供了计时支持, 所有的计划任务管理类都需要添加到该类中.
 * @author wizardc
 */
public class TimerManager
{
	//记录该类的唯一实例
	private static TimerManager _instance;
	
	//计时器执行间隔, 单位毫秒
	private int _period = 50;
	//计时器对象
	private Timer _timer;
	//计划任务管理池对象
	private TaskManagerPool _pool;
	
	/**
	 * 创建一个 TimerManager 对象.
	 */
	private TimerManager()
	{
		_timer = new Timer();
		_pool = new TaskManagerPool();
	}
	
	/**
	 * 获取该类全局唯一的实例.
	 * @return 该类全局唯一的实例.
	 */
	public static synchronized TimerManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new TimerManager();
		}
		return _instance;
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
			_pool.initialize(_period);
		}
	}
	
	/**
	 * 启动计时器对象.
	 */
	public void start()
	{
		_timer.scheduleAtFixedRate(new TaskExecutor(), 0, _period);
	}
	
	/**
	 * 获取计时器运行间隔.
	 * @return 计时器运行间隔, 单位毫秒.
	 */
	public int getPeriod()
	{
		return _period;
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
		return _pool.addTaskManager(name, taskManager);
	}
	
	/**
	 * 获取一个任务管理对象.
	 * @param name 该任务管理对象的名称.
	 * @return 指定的任务管理对象.
	 */
	public ITaskManager getTaskManager(String name)
	{
		return _pool.getTaskManager(name);
	}
	
	/**
	 * 移除一个任务管理对象.
	 * @param name 要移除的任务管理对象名称.
	 * @return 移除的任务管理对象.
	 */
	public ITaskManager removeTaskManager(String name)
	{
		return _pool.removeTaskManager(name);
	}
	
	/**
	 * 停止所有计时器任务, 下次再开启时会启动所有在暂停时到达时间的任务.
	 */
	public void stop()
	{
		_timer.cancel();
	}
	
	/**
	 * 停止并清空所有计时器任务.
	 */
	public void cleanup()
	{
		_timer.cancel();
		_pool.cleanup();
	}
	
	/**
	 * 计时器执行类.
	 * @author wizardc
	 */
	class TaskExecutor extends TimerTask
	{
		@Override
		public void run()
		{
			_pool.run();
		}
	}
}
