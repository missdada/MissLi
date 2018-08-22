package cn.manytag.manytagUtil.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	public static Map<String, Object> mapKeyValue(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return map;
	}

	public static Map<Object, Object> mapKeyValue(Object key, Object value) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(key, value);
		return map;
	}

	/**
	 * 将bean转为Map
	 * 
	 * @param bean
	 * @param datePattern
	 * @return HashMap
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> beanTomap(Object bean)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean == null) {
			return map;
		}
		PropertyDescriptor[] props = null;
		props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();

		if (props != null) {
			for (PropertyDescriptor prop : props) {
				map.put(prop.getName(), prop.getReadMethod().invoke(bean));
			}
		}
		return map;
	}
}
