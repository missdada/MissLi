package cn.manytag.manytagUtil.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jUtil {

	public static boolean isLevel(Level level) {
		if (level.equals(Logger.getRootLogger().getLevel())) {
			return true;
		}
		return false;
	}

	public static boolean isAll() {
		return isLevel(Level.ALL);
	}

	public static boolean isDebug() {
		return isLevel(Level.DEBUG);
	}

	public static boolean isInfo() {
		return isLevel(Level.INFO);
	}

	public static boolean isWarn() {
		return isLevel(Level.WARN);
	}

	public static boolean isError() {
		return isLevel(Level.ERROR);
	}

	public static boolean isFatal() {
		return isLevel(Level.FATAL);
	}

	public static boolean isOff() {
		return isLevel(Level.OFF);
	}

}
