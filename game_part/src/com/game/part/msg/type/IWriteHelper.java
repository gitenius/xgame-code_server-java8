package com.game.part.msg.type;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 写出帮助器
 * 
 * @author hjj2017
 * @since 2015/3/15
 * 
 */
public interface IWriteHelper {
	/**
	 * 写出 Buff 数据
	 * 
	 * @param msgObj
	 * @param buff
	 * 
	 */
	void writeBuff(AbstractMsgObj msgObj, IoBuffer buff);
}
