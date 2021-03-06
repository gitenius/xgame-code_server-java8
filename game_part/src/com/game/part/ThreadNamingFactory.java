package com.game.part;

import java.util.concurrent.ThreadFactory;

/**
 * 线程命名工厂
 * 
 * @author hjj2019
 * @since 2014/5/2
 * 
 */
public class ThreadNamingFactory implements ThreadFactory {
	/** 线程名称 */
	private String _tName;

	/**
	 * 设置线程名称
	 * 
	 * @param value
	 * @return 
	 * 
	 */
	public void putThreadName(String value) {
		this._tName = value;
	}

	@Override
	public Thread newThread(Runnable r) {
		if (r == null) {
			return null;
		} else {
			return new Thread(r, this._tName);
		}
	}
}
