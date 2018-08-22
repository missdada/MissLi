package cn.manytag.manytagUtil.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtils {

	// 格式：年－月－日 小时：分钟：秒
	public static final String FORMAT_yMdHms = "yyyy-MM-dd HH:mm:ss";
	// 格式：年/月/日 小时：分钟：秒
	public static final String FORMAT_yMdHms2 = "yyyy/MM/dd HH:mm:ss";

	// 格式：年－月－日 小时：分钟
	public static final String FORMAT_yMdHm = "yyyy-MM-dd HH:mm";
	// 格式：年－月－日
	public static final String FORMAT_yMd = "yyyy-MM-dd";

	public static final String yyyyMMdd = "yyyyMMdd";

	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT_yMdHms);

	public static String getDate() {
		return DATE_FORMAT.format(new Date());
	}

	public static String format(Date date) {
		return DATE_FORMAT.format(date);
	}

	public static Date string2Date(String dateStr) {
		return string2Date(dateStr, DATE_FORMAT);
	}

	/**
	 * 把符合日期格式的字符串转换为日期类型
	 * 
	 * @param dateStr 日期字符串
	 * @param format 转换格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String format) {
		return string2Date(dateStr, new SimpleDateFormat(format));
	}

	/**
	 * 出错返回null
	 * 
	 * @param dateStr
	 * @param sdf
	 * @return
	 */
	public static Date string2Date(String dateStr, SimpleDateFormat sdf) {
		Date date;
		sdf.setLenient(false);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	/**
	 * 把日期转换为字符串<br>
	 * 格式不正确返回空字符串
	 * 
	 * @param date 日期
	 * @param format 转换格式
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		String result = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			if (date != null) {
				result = formater.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}

	/**
	 * 把日期转换为字符串，不处理异常
	 * 
	 * @param date 日期
	 * @param format 转换格式
	 * @return
	 */
	public static String dateToStringException(Date date, String format) {
		String result = null;
		SimpleDateFormat formater = new SimpleDateFormat(format);
		if (date != null) {
			result = formater.format(date);
		}
		return result;
	}

	/**
	 * 获得当前日期字符串，默认格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return
	 */
	public static String getNow() {
		return dateToString(new Date(), FORMAT_yMdHms);
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @param format 格式
	 * @return
	 */
	public static String getNow(String format) {
		return dateToString(new Date(), format);
	}

	/**
	 * 两个日期相减
	 * 
	 * @return 相减得到的秒数
	 */
	public static long getTimeDiff(String firstTime, String secTime) {
		long first = stringToDate(firstTime, FORMAT_yMdHms).getTime();
		long second = stringToDate(secTime, FORMAT_yMdHms).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 获取某年某月的天数
	 */
	public static int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	 */
	public static int getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取相对于当天的日期 格式为"yyyy-MM-dd"
	 * 
	 * @param i i>0往后i天,i<0为往前i天
	 * @return
	 */
	public static String getDate(int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, i);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	/**
	 * 获取相对于date的日期 格式为"yyyy-MM-dd"
	 * 
	 * @param i i>0往后i天,i<0为往前i天
	 * @return
	 */
	public static String getDate(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, i);
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	/**
	 * 获得当前年份
	 */
	public static int getCurrYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的天
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回日期的年
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回日期的月份，1-12
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取相对于date的月份
	 * 
	 * @param i i>0往后i月,i<0为往前i月
	 * @return
	 */
	public static String getMonth(Date date, int i, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 获取相对于当前月份的月份日期
	 * 
	 * @param i i>0往后i月,i<0为往前i月
	 * @return 如:2012-12
	 */
	public static String getMonth(int i, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, i);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * 获取相对于当前年份的年份日期
	 * 
	 * @param i i>0往后i年,i<0为往前i年
	 * @return 如:2012
	 */
	public static String getYear(int i, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, i);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getMonth(-1, DateUtils.FORMAT_yMd));
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 */
	public static long getDayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}

	/**
	 * 比较指定日期与当前日期的天差
	 */
	public static long getDayDiffCurr(String before) {
		return (new Date().getTime() - stringToDate(before, FORMAT_yMd).getTime()) / 86400000;
	}

	/**
	 * 比较两个日期的年差
	 */
	public static int getYearDiff(String before, String after) {
		Date beforeDay = stringToDate(before, FORMAT_yMd);
		Date afterDay = stringToDate(after, FORMAT_yMd);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前日期的年差
	 */
	public static int getYearDiffCurr(String after) {
		return getYear(new Date()) - getYear(stringToDate(after, FORMAT_yMd));
	}

	/**
	 * 获取每月的第一周
	 */
	public static int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取每月的最后一周
	 */
	public static int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 * 
	 * @param date YYYY-mm-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	/**
	 * 参数1和参数2比较时间不比较日期，1小于2返回-1，1等于2返回0，1大于2返回1
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int compareTime(Date date, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);//小时
		int minute = calendar.get(Calendar.MINUTE);//分
		int second = calendar.get(Calendar.SECOND);//秒Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		int hour2 = calendar.get(Calendar.HOUR_OF_DAY);//小时
		int minute2 = calendar.get(Calendar.MINUTE);//分
		int second2 = calendar.get(Calendar.SECOND);//秒
		if (hour < hour2) {
			return -1;
		}
		if (hour > hour2) {
			return 1;
		}
		if (minute < minute2) {
			return -1;
		}
		if (minute > minute2) {
			return 1;
		}
		if (second < second2) {
			return -1;
		}
		if (second > second2) {
			return 1;
		}

		return 0;
	}

	/**
	 * 将时间的日期替换为今天
	 * 
	 * @param time
	 * @return
	 */
	public static Date timeToDate(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		Calendar c = Calendar.getInstance();
		calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 把字符串转化为日期类型 支持格式：yyyyMMdd，yyyyMMddHHmmss，yyyyMMddHHmmssSSS，yyyy-MM-dd
	 * HH:mm:ss，yyyy-MM-dd HH:mm:ss.SSS，yyyyMMddHHmmss
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp stringToDate(String date) {
		if (date == null || date.equals("")) {
			return null;
		}
		try {
			String formatStr = "";
			if (date.length() == 8)
				formatStr = "yyyyMMdd";
			else if (date.length() == 14)
				formatStr = "yyyyMMddHHmmss";
			else if (date.length() == 17)
				formatStr = "yyyyMMddHHmmssSSS";
			else if (date.length() == 19)
				formatStr = "yyyy-MM-dd HH:mm:ss";
			else if (date.length() == 23)
				formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
			else
				formatStr = "yyyyMMddHHmmss";

			// System.out.println("formatStr=" + formatStr + ",date.length()=" +
			// date.length());
			SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
			// System.out.println("dateFormate=" + dateFormate);
			Date d = dateFormate.parse(date);
			// System.out.println("dd=" + d);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp objectToDate(Object date) {
		if (date == null || date.equals("")) {
			return null;
		}
		try {
			String formatStr = "";
			String date1 = StringUtil.objectToString(date);
			if (date1.length() == 8)
				formatStr = "yyyyMMdd";
			else if (date1.length() == 14)
				formatStr = "yyyyMMddHHmmss";
			else if (date1.length() == 17)
				formatStr = "yyyyMMddHHmmssSSS";
			else if (date1.length() == 19)
				formatStr = "yyyy-MM-dd HH:mm:ss";
			else if (date1.length() == 23)
				formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
			else
				formatStr = "yyyyMMddHHmmss";

			// System.out.println("formatStr=" + formatStr + ",date.length()=" +
			// date.length());
			SimpleDateFormat dateFormate = new SimpleDateFormat(formatStr);
			// System.out.println("dateFormate=" + dateFormate);
			Date d = dateFormate.parse(date1);
			// System.out.println("dd=" + d);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp stringToTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		try {
			SimpleDateFormat dateFormate = null;
			if (date.length() == 8)
				dateFormate = new SimpleDateFormat("yyyyMMdd");
			else if (date.length() == 14)
				dateFormate = new SimpleDateFormat("yyyyMMddHHmmss");
			else if (date.length() == 17)
				dateFormate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			else if (date.length() == 19)
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else if (date.length() == 23)
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			else
				dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = dateFormate.parse(date);
			return new Timestamp(d.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		String sDate = date + "00000000000000000".substring(0, (17 - date.length()));

		return StringUtil.stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLStartTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		String sDate = date + "000000000";

		return StringUtil.stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToNLTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		String sDate = date + "000";

		return StringUtil.stringToLong(sDate);
	}

	/**
	 * 把字符串转化为日期数字类型
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long stringToLEndTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		String sDate = date + "235959999";

		return StringUtil.stringToLong(sDate);
	}

	/**
	 * 取日期数字型的当前时间
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Long getNowLTime() {
		return StringUtil.stringToLong(dateToString2(new Date(), "yyyyMMddHHmmssSSS"));
	}

	/**
	 * 把字符串转为日期且为当天日期类型
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp stringToEndTime(String date) {
		if (StringUtil.isEmpty(date))
			return null;
		else if (date.length() == 8)
			return stringToTime(date + "235959");
		else if (date.length() == 10)
			return stringToTime(date + " 23:59:59");
		else
			return stringToTime(date.substring(0, 8) + "235959");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static Timestamp getNowTime() {
		return new Timestamp((new Date()).getTime());
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getCurrentTime() {
		return dateToString(new Date());
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getChineseCurrentTime() {
		String sDate = getCurrentTime("yyyy-MM-dd");
		String[] aDate = sDate.split("-");
		return aDate[0] + "年" + aDate[1] + "月" + aDate[2] + "日";
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getChineseTimeByLong(Long date) {
		String sDate = "" + date;
		return sDate.substring(0, 4) + "年" + sDate.substring(4, 6) + "月" + sDate.substring(6, 8) + "日";
	}

	/**
	 * 取当前时间
	 * 
	 * @return 返回字符串类型
	 * @throws ParseException
	 */
	public static String getCurrentTime(String dateFormate) {
		return dateToString2(new Date(), dateFormate);
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String dateToString(Date date) {
		return dateToString2(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String formatStringDateToyyyyMMdd(String date) {
		String sResult = date.replace("-", "");
		sResult = sResult.replace(":", "");
		sResult = sResult.replace(" ", "");
		return sResult.substring(0, 14);
	}

	/**
	 * 把字符串转化为日期类型8
	 * 
	 * @return 返回日期类型8
	 * @throws ParseException
	 */
	public static String formatStringDateToyyyyMMdd8(String date) {
		String sResult = date.replace("-", "");
		sResult = sResult.replace(":", "");
		sResult = sResult.replace(" ", "");
		return sResult.substring(0, 8);
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String dateToStringyyMMdd(Date date) {
		return dateToString2(date, "yyyyMMdd");
	}

	/**
	 * 把字符串转化为日期类型
	 * 
	 * @return 返回日期类型
	 * @throws ParseException
	 */
	public static String stringyyyyMMddFormat(String date) {
		if (StringUtil.isEmpty(date) || date.length() < 8)
			return date;

		return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
	}

	/**
	 * 把字符串转化为日期类型<br>
	 * 格式不正确返回null
	 * 
	 * @return 返回日期类型
	 */
	public static String dateToString2(Date date, String format) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat dateFormate = new SimpleDateFormat(format);
			return dateFormate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期相减
	 * 
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((date.getTime() - date1.getTime()) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相加
	 * 
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Date dd = new Date();
		dd.setTime(date.getTime() + ((long) day) * 24 * 3600 * 1000);
		return dd;
	}

	/**
	 * 日期相减几分钟
	 * 
	 * @return 返回相减后的日期
	 */
	public static Date minusDateMinute(Date date, int minute) {
		Date dd = new Date();
		dd.setTime(date.getTime() + ((long) minute) * 60 * 1000);
		return dd;
	}

	/**
	 * 日期间隔天数
	 * 
	 * @return 返回相差的天数
	 * @throws Exception
	 */
	public static int diffDate(String date1, String date2) throws Exception {
		return diffDate(stringToDate(date1), stringToDate(date2));
	}

	/**
	 * 格式化日期：20000101 -- > 2000-01-01
	 * 
	 * @return 按-分隔的日期字符串
	 */
	public static String stringDateToDashDateString(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return "";
		str = StringUtil.padRight(str, 8, '0');
		String sYear = str.substring(0, 4);
		String sMonth = str.substring(4, 6);
		String sDay = str.substring(6, 8);
		String sNewDate = sYear + "-" + sMonth + "-" + sDay;
		return sNewDate;
	}

}
