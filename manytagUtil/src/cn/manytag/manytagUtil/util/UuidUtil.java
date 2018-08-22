package cn.manytag.manytagUtil.util;

import java.util.UUID;

public class UuidUtil {
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}