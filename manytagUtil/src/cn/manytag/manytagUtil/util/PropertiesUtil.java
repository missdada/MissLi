package cn.manytag.manytagUtil.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 属性文件工具类
 * 
 * @author hongjun.hu
 */
public class PropertiesUtil {
	private Properties properties;
	private String filePath;

	/**
	 * 
	 * @param fileName
	 *            webRoot后的文件名，不包含扩展名
	 */
	public PropertiesUtil(String fileName) {
		filePath = getPropertiesFilePath(fileName);
		try {
			FileInputStream in = new FileInputStream(filePath);
			properties = new Properties();
			properties.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object get(String str) {
		return properties.get(str);
	}

	public String getString(String str) {
		return StringUtil.valueOfNull(properties.get(str));
	}

	public int getInt(String str) {
		return Integer.parseInt(getString(str));
	}

	/**
	 * 加载指定路径的属性文件
	 * 
	 * @throws IOException
	 */
	public static Properties load(String fileName) throws IOException {
		return loadProperties(fileName, System.getProperty("file.encoding"));
	}

	public static Properties loadUtf8(String fileName) throws IOException {
		return loadProperties(fileName, "utf-8");
	}

	public static Properties loadProperties(String fileName) throws IOException {
		return loadProperties(fileName + ".properties", System.getProperty("file.encoding"));
	}

	public static Properties loadPropertiesUtf8(String fileName) throws IOException {
		return loadProperties(fileName + ".properties", "utf-8");
	}

	public static Properties loadProperties(String fileName, String charsetName) throws IOException {
		Properties properties;
		InputStreamReader in = new InputStreamReader(new FileInputStream(fileName), charsetName);
		properties = new Properties();
		properties.load(in);
		in.close();
		return properties;
	}

	/**
	 * 覆盖写入数据
	 * 
	 * @param map
	 */
	public void writeProperties(Map<String, Object> map) {
		writeProperties(filePath, map);
	}

	/**
	 * 向指定路径的属性文件覆盖写数据
	 * 
	 * @param filePath
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	public void writeProperties(String filePath, Map<String, Object> map) {
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);

			Set set = map.keySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next().toString();
				String value = map.get(key).toString();
				fos.write((key + "=" + value).getBytes());
				if (iterator.hasNext()) {
					fos.write("\n".getBytes());
				}
			}

			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeProperties(String key, String value) {
		writeProperties(filePath, key, value);
	}

	/**
	 * 向指定路径的属性文件覆盖写数据
	 * 
	 * @param filePath
	 * @param key
	 * @param value
	 */
	public void writeProperties(String filePath, String key, String value) {
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write((key + "=" + value).getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向配置文件中替换写入数据，但数据位置会随机排放，注释会被删除
	 * 
	 * @param key
	 * @param value
	 */
	public boolean setProperties(String key, Object value) {
		return setProperties(filePath, key, value);
	}

	public boolean setProperties(String filePath, String key, Object value) {
		properties.put(key, StringUtil.valueOfEmpty(value));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			properties.store(fos, null);
		} catch (IOException e) {
			return false;
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取指定文件名的属性文件路径，不需要后缀
	 * 
	 * @param filePath
	 * @return
	 */
	private String getPropertiesFilePath(String fileName) {
		return SystemPath.WEB_ROOT_PATH + fileName + ".properties";
	}
}
