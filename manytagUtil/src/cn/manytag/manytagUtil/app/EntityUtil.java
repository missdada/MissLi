package cn.manytag.manytagUtil.app;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class EntityUtil {

	/**
	 * 用obj2中不为null的属性修改obj1，并返回obj1。<br>
	 * 通过obj的属性去找其get set方法，将get返回值给set，<br>
	 * 可能报出找不到方法错误，因为有这个属性，但却没有提供对外的get方法。<br>
	 * 通过属性去获得其get方法，但是如果属性和对应的方法名称或类型不一致则报错
	 *
	 * @param obj1 此对象被修改，并返回
	 * @param obj2 将此对象不为 null 的值赋给 obj1
	 * @return 被修改后的 obj1
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 */
	public static <E> E update(E obj1, E obj2)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (obj1 == null) {
			return null;
		}
		if (obj2 == null) {
			return obj1;
		}
		for (PropertyDescriptor property : Introspector.getBeanInfo(obj2.getClass(), Object.class).getPropertyDescriptors()) {
			Object value = property.getReadMethod().invoke(obj2);
			if (value == null) {
				continue;
			} else if (property.getWriteMethod() != null) {
				property.getWriteMethod().invoke(obj1, value);
			}
		}

		return obj1;
	}

	/**
	 * 通过get方法获得其属性名，和值
	 * 
	 * @param bean
	 * @return
	 */
	public static String getJson(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = props[i].getName();
					Object value = props[i].getReadMethod().invoke(bean);
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}

		return json.toString();
	}
}
