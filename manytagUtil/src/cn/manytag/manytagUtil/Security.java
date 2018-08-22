package cn.manytag.manytagUtil;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.manytag.manytagUtil.math.AryUtil;
import cn.manytag.manytagUtil.util.StringUtil;

/**
 * 安全<br/>
 * 可以用于生成密文
 *
 */
public class Security {

	/**
	 * 生成43位64进制的密文，得到的密文是不可逆的
	 *
	 * @param data 原数据
	 * @param salt 密钥
	 * @return
	 * @throws java.security.InvalidKeyException
	 */
	public static String encryption(String data, String salt) throws InvalidKeyException {
		return Base64.getEncoder().encodeToString(sha256Byte(hmac(data, salt)));
	}

	/**
	 * md5(小写的32位16进制)
	 *
	 * @param data
	 * @return
	 */
	public static String md5(Object data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(StringUtil.valueOfEmpty(data).getBytes());
			return AryUtil.bytesToHex(md.digest());
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * sha-256
	 *
	 * @param data
	 * @return
	 */
	public static byte[] sha256Byte(Object data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(StringUtil.valueOfEmpty(data).getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * sha-256(小写的64位16进制)
	 *
	 * @param data
	 * @return
	 */
	public static String sha256(Object data) {
		return AryUtil.bytesToHex(sha256Byte(data));
	}

	/**
	 * hmacSHA256(小写的64位64进制)
	 * 
	 * @param data 数据
	 * @param key 加密使用的key
	 * @return
	 * @throws InvalidKeyException
	 */
	public static String hmac(Object data, String key) throws InvalidKeyException {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			return AryUtil.bytesToHex(mac.doFinal(StringUtil.valueOfEmpty(data).getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 返回随机值(小写的32位16进制)
	 * 
	 * @return
	 */
	public static String salt() {
		return Security.md5(Math.random());
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @param password
	 * @return
	 */
	public static String xdEncryption(String str, String password) {
		byte[] pwds = password.getBytes();
		byte bSum = 31;
		for (byte b : pwds) {
			bSum += b;
		}
		bSum ^= str.length();

		byte[] bytes = str.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] ^ bSum ^ (i == 0 ? 31 : bytes[i - 1]));
		}

		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * 解密
	 * 
	 * @param str 密文
	 * @param password 密钥
	 * @return
	 */
	public static String xdDecrypted(String str, String password) {
		byte[] pwds = password.getBytes();
		byte bSum = 31;
		for (byte b : pwds) {
			bSum += b;
		}
		bSum ^= getPasswordLen(str);
		byte[] bytes = Base64.getDecoder().decode(str.getBytes());

		byte b = 31;
		for (int i = 0; i < bytes.length; i++) {
			byte temp = (byte) (bytes[i] ^ bSum ^ b);
			b = bytes[i];
			bytes[i] = temp;
		}
		return new String(bytes);
	}

	/**
	 * 根据加密后的密文,获得xdEncryption的明文长度
	 * 
	 * @param str
	 * @return
	 */
	private static int getPasswordLen(String str) {
		int len = str.length() - str.length() / 4;
		if (str.lastIndexOf('=') > -1) {
			len--;
			if (str.lastIndexOf('=', str.length() - 2) > -1) {
				len--;
			}
		}
		return len;
	}
}
