package cn.manytag.manytagUtil.util;

public class SystemPath {
	public static final String CLASS_PATH;
	public static final String WEB_ROOT_PATH;

	static {
		String path = SystemPath.class.getClassLoader().getResource("").getPath().replace("%20", " ");
		CLASS_PATH = path;
		if (path.indexOf("classes") != -1) {
			path = path.substring(0, path.length() - 8);
		}
		if (path.indexOf("WEB-INF") != -1) {
			path = path.substring(0, path.length() - 8);
		}
		WEB_ROOT_PATH = path;
	}
}
