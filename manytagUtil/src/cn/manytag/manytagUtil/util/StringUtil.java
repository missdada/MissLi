package cn.manytag.manytagUtil.util;

import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class StringUtil {

	/**
	 * 返回字符串的副本，忽略左侧空字符
	 * 
	 * @param str
	 * @return
	 */
	public String lTrim(String str) {
		char[] val = str.toCharArray();
		int len = val.length;
		int st = 0;

		while ((st < len) && (val[st] <= ' ')) {
			st++;
		}
		return (st > 0) ? str.substring(st, len) : str;
	}

	/**
	 * 返回字符串的副本，忽略右侧空字符
	 * 
	 * @param str
	 * @return
	 */
	public String rTrim(String str) {
		char[] val = str.toCharArray();
		int len = val.length;
		int st = 0;

		while ((st < len) && (val[len - 1] <= ' ')) {
			len--;
		}
		return ((len < val.length)) ? str.substring(st, len) : str;
	}

	/**
	 * 生成指定个数的空格
	 * 
	 * @param num
	 * @return
	 */
	public static String space(int num) {
		return produceStr(" ", num);
	}

	/**
	 * 生成指定个数的字符
	 * 
	 * @param num
	 * @param str
	 * @return
	 */
	public static String produceStr(String str, int num) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < num; i++) {
			builder.append(str);
		}
		return builder.toString();
	}

	/**
	 * 根据字符串宽度右侧补空格，全角占两个宽度
	 * 
	 * @param str 原字符串
	 * @param length 需要字符串的总长度，全角占两个长度
	 * @return
	 */
	public static String addSpace(String str, int length) {
		return str + space(length - getLength(str));
	}

	/**
	 * 返回字符串长度半角占1个长度，全角占两个长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getLength(String str) {
		if (str == null) {
			throw new NullPointerException("参数不能为空");
		}
		int tmpLeng = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).getBytes().length == 1) {
				tmpLeng++;
			} else {
				tmpLeng += 2;
			}
		}
		return tmpLeng;
	}

	public static int getLength2(Object obj) {
		if (obj == null) {
			return 0;
		}
		return obj.toString().length();
	}

	/**
	 * 将字符串指定位置替换为新字符
	 * 
	 * @param str
	 * @param index
	 * @param replacement
	 * @return
	 */
	public static String replace(String str, int index, char replacement) {
		if (index < 0) {
			throw new StringIndexOutOfBoundsException(index);
		}
		if (index > str.length()) {
			throw new StringIndexOutOfBoundsException(index);
		}
		char[] chars = str.toCharArray();
		chars[index] = replacement;
		return new String(chars);
	}

	/**
	 * 验证18位身份证号
	 * 
	 * @param idNum 18位身份证号
	 */
	public static boolean CheckIDCard18(String idNum) {
		long n = 0;
		try {
			n = Long.parseLong(idNum.substring(0, 17));
			if (n < Math.pow(10, 16)) {
				return false;// 数字验证
			}
			n = Long.parseLong(idNum.replace("x", "0").replace("X", "0"));
		} catch (Exception ex) {
			return false;
		}

		String address = "11x22x35x44x53x12x23x36x45x54x13x31x37x46x61x14x32" + "x41x50x62x15x33x42x51x63x21x34x43x52x64x65x71x81x82x91";
		if (!address.contains(idNum.substring(0, 2))) {
			return false;// 省份验证
		}
		String birth = idNum.substring(6, 10) + "-" + idNum.substring(10, 12) + "-" + idNum.substring(12, 14);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(birth);
		} catch (Exception ex) {
			return false;// 生日验证
		}
		String[] arrVarifyCode = ("1,0,x,9,8,7,6,5,4,3,2").split(",");
		String[] Wi = ("7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2").split(",");
		char[] Ai = idNum.substring(0, 17).toCharArray();
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum += Integer.parseInt(Wi[i]) * Integer.parseInt(String.valueOf(Ai[i]));
		}
		int y = -1;
		y = sum % 11;
		return arrVarifyCode[y].equals(idNum.substring(17).toLowerCase());
	}

	/**
	 * return obj == null ? null : obj.toString();
	 * 
	 * @param obj
	 * @return
	 */
	public static String valueOfNull(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
	 * return obj == null ? "" : obj.toString();
	 * 
	 * @param obj
	 * @return
	 */
	public static String valueOfEmpty(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 返回指定子字符串在此字符串中第n此出现的位置<br>
	 * 不存在返回-1<br>
	 * 示例<br>
	 * 123, 12, 1 = 0<br>
	 * 1123， 12， 1 = 1<br>
	 * 1123， 12， 2 = -1<br>
	 * 
	 * @param str
	 * @param number 第几次出现，第一次值为1
	 * @return
	 */
	public static int indexOfNum(String str1, String str2, int number) {
		String value = new String(str1);
		int indexSum = 0;
		for (int i = 0; i < number; i++) {
			int index = value.indexOf(str2);
			if (index == -1) {
				return -1;
			}
			indexSum += index;
			if (i != 0) {
				indexSum++;
			}
			value = value.substring(index + 1);
		}
		return indexSum;
	}

	/**
	 * 是字符返回字符，否则返回null<br>
	 * return obj instanceof String ? (String) obj : null;
	 * 
	 * @param obj
	 * @return
	 */
	public static String isString(Object obj) {
		return obj instanceof String ? (String) obj : null;
	}

	/**
	 * 返回指定长度的字符串，从前面进行截取。长度超出str的长度，返回值不会增加长度<br>
	 * 所有字符都按1个长度计算<br>
	 * 例如：<br>
	 * 参数：123456789, 8, 超长<br>
	 * 返回：123456超长
	 * 
	 * @param str 原字符串
	 * @param len 返回字符串的长度
	 * @param msg 提示信息。如果进行了截取，在字符串尾部附加的字符串。
	 * @return
	 */
	public static String substrLen(String str, int len, String msg) {
		if (len < 0) {
			throw new IllegalArgumentException("len不能小于0");
		}
		if (str == null) {
			str = "";
		} else if (str.length() > len) {
			if (msg == null) {
				msg = "";
			}
			int temp = len - msg.length();
			if (temp < 0 || temp == 0) {
				//如果
				str = msg.substring(0, len);
			} else {
				str = str.substring(0, temp) + msg;
			}
		}
		return str;
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static String objectToString(Object obj) {
		return objectToString(obj, "");
	}

	/**
	 * Object类型转化为String类型
	 * 
	 * @return 返回String类型
	 */
	public static String objectToString(Object obj, String defValue) {
		return (obj == null) ? defValue : obj.toString();
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static double objectTodouble(Object obj) {
		return stringToDouble(objectToString(obj)).doubleValue();
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static Integer objectToInteger(Object obj) {
		return stringToInteger(objectToString(obj));
	}

	/**
	 * Object类型转化为String类型，默认为""
	 * 
	 * @return 返回String类型
	 */
	public static Integer objectToInteger(Object obj, int defValue) {
		return stringToInteger(objectToString(obj), new Integer(defValue));
	}

	/**
	 * String类型转化为Long类型，默认为null
	 * 
	 * @return 返回Long类型
	 */
	public static long stringTolong(String value) {
		return stringToLong(value, new Long(0)).longValue();
	}

	/**
	 * String类型转化为Long类型，默认为null
	 * 
	 * @return 返回Long类型
	 */
	public static Long stringToLong(String value) {
		return stringToLong(value, null);
	}

	public static String addStringValue(String value, int add) {
		if (value == null || value.equals("")) {
			return "" + add;
		}
		int olength = value.length();
		String newValue = "" + (stringTolong(value) + add);

		if (newValue.length() < olength)
			return "0000000000000000000000".substring(0, olength - newValue.length()) + newValue;
		else
			return newValue;
	}

	/**
	 * String类型转化为Long类型
	 * 
	 * @return 返回Long类型
	 */
	public static Long stringToLong(String value, Long defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Long(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Double类型，默认为null
	 * 
	 * @return 返回Double类型
	 */
	public static Double stringToDouble(String value) {
		return stringToDouble(value, null);
	}

	/**
	 * String类型转化为Double类型，默认为null
	 * 
	 * @return 返回Double类型
	 */
	public static double stringTodouble(String value) {
		return stringToDouble(value, new Double(0)).doubleValue();
	}

	/**
	 * String类型转化为Double类型
	 * 
	 * @return 返回Double类型
	 */
	public static Double stringToDouble(String value, Double defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Double(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为BigDecimal类型，默认为null
	 * 
	 * @return 返回BigDecimal类型
	 */
	public static BigDecimal stringToBigDecimal(String value) {
		return stringToBigDecimal(value, new BigDecimal(0));
	}

	/**
	 * String类型转化为BigDecimal类型
	 * 
	 * @return 返回BigDecimal类型
	 */
	public static BigDecimal stringToBigDecimal(String value, BigDecimal defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Float类型，默认为null
	 * 
	 * @return 返回Float类型
	 */
	public static Float stringToFloat(String value) {
		return stringToFloat(value, null);
	}

	/**
	 * String类型转化为Float类型
	 * 
	 * @return 返回Float类型
	 */
	public static Float stringToFloat(String value, Float defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Float(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Byte类型，默认为null
	 * 
	 * @return 返回Byte类型
	 */
	public static Byte stringToByte(String value) {
		return stringToByte(value, null);
	}

	/**
	 * String类型转化为Byte类型
	 * 
	 * @return 返回Byte类型
	 */
	public static Byte stringToByte(String value, Byte defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Byte(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Short类型，默认为null
	 * 
	 * @return 返回Short类型
	 */
	public static Short stringToShort(String value) {
		return stringToShort(value, null);
	}

	/**
	 * String类型转化为Short类型
	 * 
	 * @return 返回Short类型
	 */
	public static Short stringToShort(String value, Short defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Short(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * String类型转化为Integer类型，默认为null
	 * 
	 * @return 返回Integer类型
	 */
	public static Integer stringToInteger(String value) {
		return stringToInteger(value, null);
	}

	/**
	 * String类型转化为Integer类型
	 * 
	 * @return 返回Integer类型
	 */
	public static Integer stringToInteger(String value, Integer defValue) {
		if (value == null || value.equals("")) {
			return defValue;
		}
		try {
			return new Integer(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 格式化字符串,如："1,2,3"格式化为"'1','2','3'"
	 * 
	 * @param str
	 * @return 返回加上引号的字符串
	 */
	public static String formatCommaString(String str) {

		if (str == null || str.trim().equals(""))
			return "";

		String[] aTmpStr = str.split(",");
		String sFormatStr = "";
		for (int i = 0; i < (aTmpStr.length - 1); i++) {
			sFormatStr += "'" + aTmpStr[i] + "',";
		}
		sFormatStr += "'" + aTmpStr[aTmpStr.length - 1] + "'";
		return sFormatStr;
	}

	/**
	 * @param 填充的字符串
	 * @param 字符串要返回的长度
	 * @param 字符串在长度不够时 ，左边填充的字符
	 * @return 长度为length的字符串
	 */
	public static String padLeft(String s, int len, char c) {

		if (s == null) {
			s = "";
		}
		s = s.trim();
		if (s.length() >= len) {
			return s;
		}

		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();

		while (fill-- > 0) {
			d.append(c);
		}

		d.append(s);
		return d.toString();
	}

	/**
	 * @param 填充的字符串
	 * @param 字符串要返回的长度
	 * @param 字符串在长度不够时 ，右边填充的字符
	 * @return 长度为length的字符串
	 */
	public static String padRight(String s, int len, char c) {

		if (s == null) {
			s = "";
		}
		s = s.trim();
		if (s.length() >= len) {
			return s;
		}

		StringBuffer d = new StringBuffer(len);
		int fill = len - s.length();

		while (fill-- > 0) {
			d.append(c);
		}

		return s.concat(d.toString());
	}

	/**
	 * 字符串进行重复count次
	 * 
	 * @param 待重复字符串
	 * @param 重复次数
	 * @return
	 */
	public static String getRepeatString(String s, int count) {
		StringBuffer sb = new StringBuffer();
		while (count-- > 0)
			sb.append(s);
		return sb.toString();
	}

	/**
	 * 检查日期是否为空
	 */
	public static boolean isEmpty(Date dd) {
		return dd == null;
	}

	/**
	 * 检查对象是否为空
	 */
	public static boolean isEmpty(Object obj) {
		String ss = objectToString(obj);
		return ss == null || ss.trim().equals("");
	}

	/**
	 * 检查对象是否为空
	 */
	public static boolean isEmpty(@SuppressWarnings("rawtypes") HashMap map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 检查字符串是否为空
	 */
	public static boolean isEmpty(String ss) {
		return ss == null || ss.trim().equals("");
	}

	/**
	 * 检查长整型是否为空
	 */
	public static boolean isEmpty(Long ll) {
		return ll == null;
	}

	/**
	 * 检查整型是否为空
	 */
	public static boolean isEmpty(Integer ii) {
		return ii == null;
	}

	/**
	 * 检查手机号段属于哪个公司
	 * 
	 * @param cellphoneNumber 手机号
	 * @return 1-移动，2-电信，3-联通
	 */
	public static int cellphoneNumberBelongTo(String cellphoneNumber) {
		String cellYD = "134.135.136.137.138.139.150.151.152.157.158.159.187.188"; // 移动
		String cellDX = "133.153.180.189"; // 电信
		String cellLT = "130.131.132.155.156.185.186"; // 联通
		String cellHead = cellphoneNumber.substring(0, 3);

		if (cellYD.contains(cellHead))
			return 1;
		else if (cellDX.contains(cellHead))
			return 2;
		else if (cellLT.contains(cellHead))
			return 3;
		else
			return 0;
	}

	public static String getAllMapKeyName2(HashMap<String, Object> map) {
		Object[] sKey = map.keySet().toArray();
		String result = "";
		for (int i = 0; i < sKey.length; i++) {
			if (result.equals(""))
				result += sKey[i].toString();
			else
				result += "," + sKey[i].toString();
		}
		return result;
	}

	/**
	 * 将map中key修改为驼峰命名方式并返回新的map
	 * 
	 * @param map
	 * @return
	 */
	public static HashMap<String, Object> getAllMapKeyName2AndUpdateMap(HashMap<String, Object> map) {
		Object[] sKey = map.keySet().toArray();
		for (int i = 0; i < sKey.length; i++) {
			if (sKey[i].toString().contains("_")) {
				map.put(camelName(sKey[i].toString()), map.get(sKey[i].toString()));// 添加驼峰key
				map.remove(sKey[i]);// 移除旧值
			}
		}
		return map;
	}

	public static String getAllMapKeyName(HashMap<String, String> map) {
		Object[] sKey = map.keySet().toArray();
		String result = "";
		for (int i = 0; i < sKey.length; i++) {
			if (result.equals(""))
				result += sKey[i].toString();
			else
				result += "," + sKey[i].toString();
		}
		return result;
	}

	/**
	 * 拼接字符串
	 * 
	 * @param target 目标字符串
	 * @param join 需要拼接的字符串
	 * @return
	 */
	public static String getSplitJointStr(String target, String join, String... connect) {
		String sResult = "";
		String cnn = ",";
		if (connect.length > 0)
			cnn = connect[0];

		if (isEmpty(target) && (!isEmpty(join)))
			sResult = join;
		else if ((!isEmpty(target)) && (isEmpty(join)))
			sResult = target;
		else if ((!isEmpty(target)) && (!isEmpty(join)))
			sResult = target + cnn + join;

		return sResult;
	}

	public static String getSubStringByIndex(String source, int index, String... separator) {
		if (isEmpty(source))
			return "";
		if (index < 1)
			return "";

		String sep = ",";
		if (separator.length > 0)
			sep = separator[0];

		String[] aSource = source.split(sep);
		if (index > aSource.length)
			return "";
		return aSource[index - 1];
	}

	/**
	 * 大写段转为字符串
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String oracleClob2Str(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : "");
	}

	public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
		Method methodToInvoke = lob.getClass().getMethod("getCharacterOutputStream", (Class[]) null);
		Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
		writer.write(str);
		writer.close();
		return lob;
	}

	public static double getDoubleRound(double dub) {
		return Math.round(dub * 100) / 100.0;
	}

	public static String trimDouble0(double dub) {
		return formatDouble(dub, "#0.##");
	}

	public static String formatDouble(double dub, String fromat) {
		DecimalFormat df = new DecimalFormat(fromat);
		return df.format(dub);
	}

	/**
	 * 把数字格式化成字符串
	 * 
	 * @param i
	 * @param format
	 * @return
	 */
	public static String formatNum(int i, String format) {
		if (isEmpty(format)) {
			return "" + i;
		}
		try {
			DecimalFormat df = new DecimalFormat();
			df.applyPattern(format);// 将格式应用于格式化器
			return df.format(i);
		} catch (Exception e) {
			return "" + i;
		}
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			// System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String getURLDecoder(String type, String value) throws Exception {
		try {
			value = value.replaceAll(type, "%");
			value = URLDecoder.decode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	public static String getURLDecoder(String value) throws Exception {
		try {
			value = URLDecoder.decode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	public static String getURLEecoder(String value) throws Exception {
		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return value;
	}

	/**
	 * 字符串分割成整型数组
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Long[] getLongArrayFromStr(String str) throws Exception {

		if (StringUtil.isEmpty(str))
			return (new Long[0]);

		String[] aStr = str.split(",");
		Long[] iIDs = new Long[aStr.length];
		for (int i = 0; i < aStr.length; i++) {
			iIDs[i] = StringUtil.stringToLong(aStr[i]);
		}
		return iIDs;
	}

	/**
	 * 字符串分割成整型数组
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String[] getStringArrayFromStr(String str) throws Exception {

		if (StringUtil.isEmpty(str))
			return (new String[0]);

		return str.split(",");
	}

	public static int getSkipRows(String currentPage, String rowsPerPage) {
		if (isEmpty(currentPage) || isEmpty(rowsPerPage))
			return 0;

		int skipRows = (StringUtil.objectToInteger(currentPage) - 1) * StringUtil.objectToInteger(rowsPerPage);

		if (skipRows < 0)
			return 0;
		else
			return skipRows;
	}

	public static int getSkipRows(Object currentPage, Object rowsPerPage) {
		return getSkipRows(StringUtil.objectToString(currentPage), StringUtil.objectToString(rowsPerPage));
	}

	public static int getLimitRows(String rowsPerPage) {
		if (isEmpty(rowsPerPage))
			return 100;

		return StringUtil.objectToInteger(rowsPerPage);
	}

	public static int getLimitRows(Object rowsPerPage) {
		return getLimitRows(StringUtil.objectToString(rowsPerPage));
	}

	/**
	 * JQuery EasyUI设置分页和排序
	 * 
	 * @param map
	 */
	public static void setPageAndOrder(HashMap<String, Object> map) {
		int skipRows = StringUtil.getSkipRows(map.get("page"), map.get("rows"));// page和rows
																				// JQuery
																				// EasyUI的datagrid里用到的参数
		int limitRows = StringUtil.getLimitRows(map.get("rows"));

		map.put("skipRows", skipRows);
		map.put("limitRows", limitRows);

		String sort = StringUtil.objectToString(map.get("sort"), "id");// 排序字段
		String order = StringUtil.objectToString(map.get("order"), "asc");// 排序方式

		map.put("orderby", sort + " " + order);
	}

	/**
	 * 按日期字符串yyyyMMdd取其所在的财务月
	 * 
	 * @param sInDate
	 * @return
	 */
	public static String getFinanceMonthByDate(String sInDate) {
		String sDate = sInDate.substring(0, 8);
		int sYear = stringToInteger(sDate.substring(0, 4));
		int iMonth = stringToInteger(sDate.substring(4, 6));
		int iDay = stringToInteger(sDate.substring(6, 8));

		/*
		 * if ((iMonth == 1) && (iDay <= 25)) return sYear + "01"; else
		 * if(((iMonth == 11) && (iDay >= 26)) || (iMonth == 12)) return sYear +
		 * "12"; else if(iDay >= 26) return "" + (sYear * 100 + iMonth + 1);
		 * else return "" + (sYear * 100 + iMonth);
		 */

		if ((iMonth <= 11) && (iDay >= 26))
			return "" + (sYear * 100 + iMonth + 1);
		else
			return "" + (sYear * 100 + iMonth);
	}

	/**
	 * @param ss
	 * @param sub
	 * @return
	 */
	public static String deleteSubString(String ss, String sub) {
		if (isEmpty(ss) || isEmpty(sub))
			return ss;

		String[] aa = ss.split(",");
		StringBuffer sb = new StringBuffer();

		for (String str : aa) {
			if (!str.equals(sub))
				sb.append(str + ",");
		}

		if (isEmpty(sb))
			return "";
		else
			return sb.substring(0, sb.length() - 1).toString();
	}

	/**
	 * 在一个字符串内加上一段号码
	 * 
	 * @param ss 原字符串
	 * @param startNo 开始号
	 * @param endNo 结束号
	 * @return
	 */
	public static String addStringSection(String ss, String startNo, String endNo) {
		if (isEmpty(startNo) || isEmpty(endNo))
			return ss;

		String result = ss;
		if (result == null)
			result = "";

		String tmp = startNo;
		if (!result.contains(tmp))
			result += "," + tmp;

		while (!tmp.equals(endNo)) {
			tmp = addStringValue(tmp, 1);
			if (!result.contains(tmp))
				result += "," + tmp;
		}

		if (result.substring(0, 1).equals(","))
			return result.substring(1, result.length());
		else
			return result;
	}

	/**
	 * 在一个字符串内加删除一段号码
	 * 
	 * @param ss 原字符串
	 * @param startNo 开始号
	 * @param endNo 结束号
	 * @return
	 */
	public static String delStringSection(String ss, String startNo, String endNo) {
		if (isEmpty(startNo) || isEmpty(endNo))
			return ss;

		String result = ss;
		if (result == null)
			result = "";

		String tmp = startNo;
		if (result.contains(tmp))
			result = deleteSubString(result, tmp);

		while (!tmp.equals(endNo)) {
			tmp = addStringValue(tmp, 1);
			if (result.contains(tmp))
				result = deleteSubString(result, tmp);
		}

		return result;
	}

	public static String getCompareType(String CompareSign) {
		if (isEmpty(CompareSign))
			return "$gte";

		if (CompareSign.equals(">"))
			return "$gt";
		else if (CompareSign.equals(">="))
			return "$gte";
		else if (CompareSign.equals("<="))
			return "$lte";
		else if (CompareSign.equals("<"))
			return "$lt";
		else
			return "$gte";
	}

	public static String replaceStr(String str) {
		String value = str.replaceAll("__r__n__", "\r\n").replace("__r__", "\r").replace("__n__", "\n").replace("__tab__", "\t").replace("“", "\"")
				.replace("‘", "'").replace("/", "\\").replace("_dq_", "“").replace("_sq_", "‘").replace("；", ";");

		return value;
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 20131212235900 -> 2013-12-12 23:59:00
	 * 
	 * @param str
	 * @return
	 */
	public static String Integer2StringDate(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = padRight(str, 12, '0');
		String sYear = str.substring(0, 4);
		String sMonth = str.substring(4, 6);
		String sDay = str.substring(6, 8);
		String sHour = str.substring(8, 10);
		String sMin = str.substring(10, 12);
		String sSec = str.substring(12, 14);
		String sNewDate = sYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMin + ":" + sSec;
		return sNewDate;
	}

	/**
	 * 20131212235900 <-- 2013-12-12 23:59:00
	 * 
	 * @param str
	 * @return
	 */
	public static String StringDate2Intgeter(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = str.replace(" ", "");
		str = str.replace(":", "");
		str = str.replace("-", "");
		return str;
	}

	/**
	 * 构造where条件
	 * 
	 * @param where
	 * @return
	 */
	public static String buildWhere(String... where) {
		StringBuilder sb = new StringBuilder();
		String str = "";
		for (String temp : where) {
			if (StringUtil.isEmpty(temp))
				;
			else {
				temp = temp + " AND ";
				sb.append(temp);
			}
		}
		if (sb.length() != 0) { // 说明有条件存在
			str = sb.substring(0, sb.length() - 4);// 去除最后一个AND
		}
		return str;
	}

	public static String buildOrWhere(String... where) {
		StringBuilder sb = new StringBuilder();
		String str = "";
		for (String temp : where) {
			if (StringUtil.isEmpty(temp))
				;
			else {
				temp = temp + " OR ";
				sb.append(temp);
			}
		}
		if (sb.length() != 0) { // 说明有条件存在
			str = sb.substring(0, sb.length() - 3);// 去除最后一个OR
		}
		return str;
	}

	/**
	 * 模糊查询条件
	 * 
	 * @param Filed
	 * @param str
	 * @return
	 */
	public static String fuzzyQuery(String Filed, String str) {
		String result = "";
		if (StringUtil.isEmpty(str)) {
			str = "";
		}
		result = " " + Filed + " like " + "'%" + str + "%'";
		return result;
	}

	/**
	 * list选择查询条件
	 * 
	 * @param Filed
	 * @param str
	 * @return
	 */
	public static String listQuery(String Filed, String str) {
		String result = "";
		if (isEmpty(str)) {
			return "";
		}
		result = " " + Filed + " = " + "'" + str + "'";
		return result;
	}

	/**
	 * 组件一个 带括号的 or
	 * 
	 * @param where
	 * @return
	 */
	public static String buildOrWhereBracket(String... where) {
		StringBuilder sb = new StringBuilder();
		String str = "";
		for (String temp : where) {
			if (StringUtil.isEmpty(temp))
				;
			else {
				temp = temp + " OR ";
				sb.append(temp);
			}
		}
		if (sb.length() != 0) { // 说明有条件存在
			str = " ( " + sb.substring(0, sb.length() - 3) + " ) ";// 去除最后一个OR
		}
		return str;
	}

	/**
	 * list2String数组
	 * 
	 * @param list
	 * @return
	 */
	public static String[] StringList2Array(List<?> list) {
		int number = list.size();
		if (number == 0) {
			return null;
		}
		String str[] = new String[number];
		for (int i = 0; i < number; i++) {
			str[i] = list.get(i).toString();
		}
		return str;
	}

	public static String GBK(String typeValue) throws UnsupportedEncodingException {
		typeValue = new String(typeValue.getBytes("ISO8859-1"), "UTF-8");
		return typeValue;
	}
}
