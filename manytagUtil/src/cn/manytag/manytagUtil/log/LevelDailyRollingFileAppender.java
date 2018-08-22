package cn.manytag.manytagUtil.log;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;

/**
 * log4j日志级别细化控制 增加了最大级别的属性
 *
 */
public class LevelDailyRollingFileAppender extends DailyRollingFileAppender {

	/**
	 * 最大级别
	 */
	protected Priority levelMax;

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {
		//判断threshold优先级 和 levelMax优先级
		return (threshold == null || priority.isGreaterOrEqual(threshold)) && (levelMax == null || levelMax.toInt() >= priority.toInt());
	}

	public Priority getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(Priority levelMax) {
		this.levelMax = levelMax;
	}
}