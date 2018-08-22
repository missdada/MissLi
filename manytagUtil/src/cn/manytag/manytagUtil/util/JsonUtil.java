package cn.manytag.manytagUtil.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import cn.manytag.manytagUtil.constant.AppConstant;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * json转换工具类
 */
public class JsonUtil {

	private JsonConfig jsonConfig;

	private static final JsonValueProcessor NULL_TO_SPACK_PROCESSOR = new JsonValueProcessor() {
		private Object process(Object value) {
			if (value == null) {
				return "";
			}
			return value;
		}

		public Object processArrayValue(Object arg0, JsonConfig arg1) {
			return process(arg0);
		}

		public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
			return process(arg1);
		}
	};

	public JsonConfig getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(JsonConfig jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	/**
	 * 将json字符串转换成map
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return getMap4Json(jsonObject);
	}

	public static Map<String, Object> getMap4Json(JSONObject json) {
		@SuppressWarnings("unchecked")
		Set<Entry<String, Object>> set = json.entrySet();

		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (Entry<String, Object> e : set) {
			valueMap.put(e.getKey(), e.getValue());
		}
		return valueMap;
//		@SuppressWarnings("unchecked")
//		Iterator<String> keyIter = json.keys();
//		String key;
//		Map<String, Object> valueMap = new HashMap<String, Object>();
//
//		while (keyIter.hasNext()) {
//			key = keyIter.next();
//			valueMap.put(key, json.get(key));
//		}
//		return valueMap;
	}

	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}

	public static String object2json(Object obj) {
		return object2json(obj, null);
	}

	public static String object2jsonDateFormat(Object bean) {
		return object2json(bean, DateUtils.FORMAT_yMdHms);
	}

	public static String toJson(String str, Object obj) {
		return "{\"" + str + "\":" + object2json(obj) + "}";
	}

	public static JSONObject toJsonObj(String str, Object obj) {
		JSONObject json = new JSONObject();
		json.put(str, obj);
		return json;
	}

	/**
	 * 
	 * @param obj
	 * @param jsonConfig
	 * @return
	 */
	public static String object2json(Object obj, String datePattern) {
		if (obj instanceof String) {
			return "\"" + obj + "\"";
		} else if (obj == null || obj instanceof BigDecimal || obj instanceof BigInteger || obj instanceof Integer || obj instanceof Float
				|| obj instanceof Boolean || obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof Byte
				|| obj instanceof Character) {
			return String.valueOf(obj);
		} else if (obj instanceof Object[]) {
			return array2json((Object[]) obj);
		} else if (obj instanceof Map) {
			return map2json((Map<?, ?>) obj, datePattern);
		} else if (obj instanceof Collection) {
			return collection2json((Collection<?>) obj, datePattern);
		} else if (obj instanceof Date && datePattern != null) {
			return "\"" + DateUtils.dateToString((Date) obj, datePattern) + "\"";
		} else if (obj instanceof Enum) {
			return "\"" + obj.toString() + "\"";
		} else {
			return bean2json(obj, datePattern);
		}
	}

	public static String object2json2(Object obj, String datePattern) {
		if (obj instanceof String) {
			return "\"" + obj + "\"";
		} else if (obj == null || obj instanceof BigDecimal || obj instanceof BigInteger || obj instanceof Integer || obj instanceof Float
				|| obj instanceof Boolean || obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof Byte
				|| obj instanceof Character) {
			return String.valueOf(obj);
		} else if (obj instanceof Object[]) {
			return array2json((Object[]) obj);
		} else if (obj instanceof Map) {
			return map2json((Map<?, ?>) obj, datePattern);
		} else if (obj instanceof Collection) {
			return collection2json((Collection<?>) obj, datePattern);
		} else if (obj instanceof Date) {
			if (datePattern == null) {
				return String.valueOf(((Date) obj).getTime());
			}
			return "\"" + DateUtils.dateToString((Date) obj, datePattern) + "\"";
		} else if (obj instanceof Enum) {
			return "\"" + obj.toString() + "\"";
		} else {
			return bean2json(obj, datePattern);
		}
	}

	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}

		return json.toString();
	}

	public static String collection2json(Collection<?> coll) {
		return collection2json(coll, null);
	}

	public static String collection2jsonDateFormat(Collection<?> coll) {
		return collection2json(coll, DateUtils.FORMAT_yMdHms);
	}

	public static String collection2json(Collection<?> coll, String datePattern) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (coll != null && coll.size() > 0) {
			for (Object obj : coll) {
				json.append(object2json(obj, datePattern));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}

		return json.toString();
	}

	public static String map2json(Map<?, ?> map) {
		return map2json(map, null);
	}

	public static String map2json(Map<?, ?> map, String datePattern) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key, datePattern));
				json.append(":");
				json.append(object2json(map.get(key), datePattern));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}

		return json.toString();
	}

	public static String bean2json(Object bean) {
		return bean2json(bean, null);
	}

	public static String bean2jsonDateFormat(Object bean) {
		return bean2json(bean, DateUtils.FORMAT_yMdHms);
	}

	public static String bean2json(Object bean, String datePattern) {
		if (bean == null) {
			return null;
		}
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
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(bean), datePattern);
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

	public static String bean2json2(Object bean) {
		return bean2json2(bean, null);
	}

	public static String bean2json2(Object bean, String datePattern) {
		if (bean == null) {
			return null;
		}
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
					String name = object2json(props[i].getName());
					String value = object2json2(props[i].getReadMethod().invoke(bean), datePattern);
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

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> getList4Json(String jsonString, Class<E> pojoClass) {
		List<E> list = new ArrayList<E>();
		if (StringUtils.isBlank(jsonString)) {
			return list;
		}

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		//TODO  通过pojoClass生成对象
		Object pojoValue;

		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add((E) pojoValue);
		}
		return list;
	}

	/**
	 * 根据bean的set方法将json内容写入其中
	 * 
	 * @param bean
	 * @param json
	 * @return
	 */
	public static <E> E beanSetjson(E bean, JSONObject json) {
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				Object obj = json.get(props[i].getName());
				if (obj != null) {
					try {
						props[i].getWriteMethod().invoke(bean, obj);
					} catch (Exception e) {
					}
				}
			}
		}
		return bean;
	}

	/**
	 * 根据beanClass的set方法将bean或json内容写入其中
	 * 
	 * @param bean
	 * @param json
	 * @return
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <E> E beanSetjson(Object bean, JSONObject json, Class<E> beanClass)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		E toBean = null;
		try {
			toBean = beanClass.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		PropertyDescriptor[] props = null;
		props = Introspector.getBeanInfo(beanClass, Object.class).getPropertyDescriptors();
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				Object obj = null;
				try {
					obj = props[i].getReadMethod().invoke(bean);
				} catch (Exception e1) {
				}
				if (obj != null) {
					props[i].getWriteMethod().invoke(toBean, obj);
				} else if (!json.isNullObject()) {
					obj = json.get(props[i].getName());
					if (obj != null) {
						props[i].getWriteMethod().invoke(toBean, obj);
					}
				}
			}
		}
		return toBean;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaObj(Object javaObj) {
		return JSONObject.fromObject(javaObj).toString();
	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj java对象
	 * @param dataFormat 日期格式
	 * @return
	 */
	public static String getJsonString4JavaObj(Object javaObj, String dataFormat) {
		JsonConfig jsonConfig = JsonUtil.configJson(dataFormat);
		if (javaObj instanceof List) {
			return JSONArray.fromObject(javaObj, jsonConfig).toString();
		} else {
			return JSONObject.fromObject(javaObj, jsonConfig).toString();
		}
	}

	/**
	 * 将带有日期的java对象转换成json字符串,默认格式 yyyy-MM-dd
	 * 
	 * @param javaObj java对象
	 * @param dataFormat 日期格式
	 * @return
	 */
	public static String getJsonString4JavaWishDate(Object javaObj) {
		return JsonUtil.getJsonString4JavaObj(javaObj, null);
	}

	public static JsonConfig configNumberJson() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Short.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Integer.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Long.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Float.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Double.class, NULL_TO_SPACK_PROCESSOR);
		return jsonConfig;
	}

	public static JsonConfig configJsonNullBooleanNumber() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Short.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Integer.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Long.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Float.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Double.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(Boolean.class, NULL_TO_SPACK_PROCESSOR);
		return jsonConfig;
	}

	/**
	 * JSON 格式解析器
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
		jsonConfig.registerJsonValueProcessor(Integer.class, NULL_TO_SPACK_PROCESSOR);
		jsonConfig.registerJsonValueProcessor(BigDecimal.class, NULL_TO_SPACK_PROCESSOR);
		return jsonConfig;
	}

	public static JsonConfig configDateJson() {
		return configDateJson(AppConstant.DATE_TIME_FORMAT);
	}

	/**
	 * JSON 日期格式解析器
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configDateJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	/**
	 * 将两个json合并在一起，生成新json
	 * 
	 * @param json1
	 * @param json2
	 * @return
	 */
	public static JSONObject merge(JSONObject... jsonArr) {
		JSONObject re = new JSONObject();
		for (int i = 0; i < jsonArr.length; i++) {
			JSONObject json = jsonArr[i];
			@SuppressWarnings("unchecked")
			Set<Entry<String, Object>> set = json.entrySet();

			for (Entry<String, Object> e : set) {
				re.put(e.getKey(), e.getValue());
			}
		}
		return re;
	}

	/**
	 * {"key":null}返回null<br>
	 * 适用于2.4
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Object get(JSONObject json, String key) {
		if (json.isNullObject()) {
			return null;
		}
		Object obj = json.get(key);
		return obj instanceof JSONNull ? null : obj;
	}

	/**
	 * 从json中取回字符串<br>
	 * {"key":null}返回null<br>
	 * 适用于2.4<br>
	 * Object obj = json.get(key);<br>
	 * return obj instanceof JSONNull ? null : StringUtil.valueOfNull(obj);
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject json, String key) {
		Object obj = get(json, key);
		return obj == null ? null : StringUtil.valueOfNull(obj);
	}

	/**
	 * {"key":null}返回null<br>
	 * 适用于2.4
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Number getNumber(JSONObject json, String key) {
		if (json.isNullObject()) {
			return null;
		}
		Object obj = json.get(key);
		return obj instanceof JSONNull ? null : (Number) obj;
	}

	/**
	 * {"key":null}返回null<br>
	 * 适用于2.4
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Integer getInt(JSONObject json, String key) {
		Number number = getNumber(json, key);
		return number == null ? null : number.intValue();
	}

	/**
	 * {"key":null}返回null<br>
	 * 适用于2.4
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Double getDouble(JSONObject json, String key) {
		Number number = getNumber(json, key);
		return number == null ? null : number.doubleValue();
	}

	/**
	 * {"key":null}返回null<br>
	 * 适用于2.4
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static Boolean getBoolean(JSONObject json, String key) {
		return (Boolean) get(json, key);
	}

}
