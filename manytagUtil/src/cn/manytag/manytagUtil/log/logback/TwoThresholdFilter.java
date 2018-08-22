package cn.manytag.manytagUtil.log.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 带有两个级别限制的过滤器
 *
 */
public class TwoThresholdFilter extends Filter<ILoggingEvent> {
	Level level;
	Level maxLevel;

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (!isStarted()) {
			return FilterReply.NEUTRAL;
		}
		if (event.getLevel().isGreaterOrEqual(level) && (maxLevel == null || event.getLevel().levelInt <= maxLevel.levelInt)) {
			return FilterReply.NEUTRAL;
		} else {
			return FilterReply.DENY;
		}
	}

	public void setLevel(String level) {
		this.level = Level.toLevel(level);
	}

	public void setMaxLevel(String level) {
		this.maxLevel = Level.toLevel(level);
	}

	public void start() {
		if (this.level != null) {
			super.start();
		}
	}
}
