package cn.manytag.manytagUtil.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import cn.manytag.manytagUtil.constant.AppConstant;

public class DateJsonValueProcessor implements JsonValueProcessor {
	private String format = AppConstant.DATE_FORMAT;

	public DateJsonValueProcessor() {

	}

	public DateJsonValueProcessor(String datePattern) {
		if (null != datePattern) {
			format = datePattern;
		}
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	public Object processObjectValue(String arg0, Object value,
			JsonConfig jsonConfig) {
		if (value instanceof Integer) {
			if (value == "" || value == null) {
				return null;
			}
		}

		if (value instanceof Date) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		if (null == value) {
			return "";
		} else {
			return value.toString();
		}
	}
}
