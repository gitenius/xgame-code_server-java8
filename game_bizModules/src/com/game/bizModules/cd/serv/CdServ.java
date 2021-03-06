package com.game.bizModules.cd.serv;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.game.bizModules.cd.CdLog;
import com.game.bizModules.cd.dao.CdTimerDao;
import com.game.bizModules.cd.entity.CdTimerEntity;
import com.game.bizModules.cd.model.CdTimer;
import com.game.bizModules.cd.model.CdTypeEnum;
import com.game.bizModules.human.Human;
import com.game.bizModules.human.IHumanEventListen;
import com.game.part.util.Assert;

/**
 * 冷却队列服务
 * 
 * @author haijiang.jin
 * 
 */
public final class CdServ implements IHumanEventListen, IServ_CanAddTime, IServ_DoAddTime, IServ_FindAndDoAddTime, IServ_KillCdTime {
	/** 单例对象 */
	public static final CdServ OBJ = new CdServ();
	/** 管理器字典 */
	final ConcurrentHashMap<Long, CdManager> _mngrMap = new ConcurrentHashMap<>();

	/**
	 * 类默认构造器
	 * 
	 */
	private CdServ() {
	}

	@Override
	public void onLoadDB(Human h) {
		if (h == null) {
			return;
		}

		// 获取管理器对象
		CdManager mngr = this._mngrMap.get(h._UUID);

		if (mngr == null) {
			mngr = new CdManager(h._UUID);
			// 添加到字典
			CdManager orig = this._mngrMap.putIfAbsent(
				h._UUID, mngr
			);

			if (orig != null) {
				CdLog.LOG.warn("Cd 管理器不为空, 角色 = " + h._UUID);
				mngr = orig;
			}
		}

		// 获取 Cd 计时器列表
		List<CdTimerEntity> el = CdTimerDao.OBJ.listByHumanUUID(h._UUID);

		if (el != null && 
			el.isEmpty() == false) {
			// 定义临时字典
			Map<CdTypeEnum, CdTimer> tmpMap = null;
			// 将实体对象转换为业务对象, 
			// 并添加到字典!
			tmpMap = el.stream()
				.map(e -> new CdTimer(
					CdTypeEnum.parse(e._cdTypeInt), 
					e._startTime, 
					e._endTime
				)).collect(Collectors.toMap(
					t -> t._cdType, t -> t
				));
	
			// 添加到管理器字典
			mngr._cdMap.putAll(tmpMap);
		}
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 
	 * 
	 */
	public long getCurrTime() {
		return 0L;
	}

	/**
	 * 检查计时器是否已过期并重置
	 * 
	 * @param t
	 * @return
	 * 
	 */
	boolean checkExpiredAndReset(CdTimer t) {
		// 断言参数不为空
		Assert.notNull(t, "t");

		if (t._endTime <= this.getCurrTime()) {
			// 如果定时器已过期, 
			// 即, 结束时间 <= 当前时间, 
			// 则重置计时器!
			t._startTime = 0L;
			t._endTime = 0L;
			return true;
		} else {
			// 如果定时器还没过期, 
			// 则直接退出!
			return false;
		}
	}
}
