package cn.manytag.manytagUtil.math;

public class AryUtil {

	/**
	 * 把byte数组转化为对应长度的16进制数
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				strBuilder.append("0");
			}
			strBuilder.append(hex);
		}
		return strBuilder.toString();
	}
}
