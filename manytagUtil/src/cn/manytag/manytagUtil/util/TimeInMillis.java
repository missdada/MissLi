package cn.manytag.manytagUtil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeInMillis {
	public static int day = 0;
	public static int night = -1;
	public static String[] weeks = new String[]{"星期天","星期一","星期二","星期三","星期四","星期五","星期六"};

	/**获得本月第一天0点时间 
	 * 
	 * @return
	 */
	public static int getTimesMonthmorning(){ 
		Calendar cal = Calendar.getInstance(); 
		cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0); 
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		return (int) (cal.getTimeInMillis()/1000); 
	} 
	/**获得本月最后一天24点时间 
	 * 
	 * @return
	 */
	public static int getTimesMonthnight(){ 
		Calendar cal = Calendar.getInstance(); 
		cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0); 
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		cal.set(Calendar.HOUR_OF_DAY, 24); 
		return (int) (cal.getTimeInMillis()/1000); 
	}
	/**获得今天时间
	 * 0 白天，-1晚上
	 * @param day_or_night
	 * @return
	 * @throws ParseException
	 */
	public static int getTimesToday(int day_or_night) throws ParseException{//day 0, night -1 
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar cal = Calendar.getInstance(); 
		String dateString = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONDAY)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+" "+(day_or_night==day?0:24)+":0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (int)(s/1000);
	}
	/**获得包括今天的之前任意天的凌晨0点时间
	 * 
	 * @param days
	 * @return
	 * @throws ParseException
	 */
	public static int getTimesTodayBeforemorning(int days) throws ParseException{ 
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar cal = Calendar.getInstance(); 
		String dateString = cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONDAY)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+" 0:0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (int)(s/1000-3600*24*days);
	}
	/**获得某天凌晨时间戳
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static long getAnyTime(int year, int month, int day) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateString = year+"/"+month+"/"+day+" 0:0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (s/1000);
	}
	/**获得某天凌晨时间戳
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static long getAnyTime(String time) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String[] d = time.split("-");
        String dateString = d[0]+"/"+d[1]+"/"+d[2]+" 0:0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (s/1000);
	}
	/**获得某天午夜时间戳
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static long getAnyTime_night(int year, int month, int day) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String dateString = year+"/"+month+"/"+day+" 24:0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (s/1000);
	}
	/**获得某天午夜时间戳
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static long getAnyTime_night(String time) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String[] d = time.split("-");
        String dateString = d[0]+"/"+d[1]+"/"+d[2]+" 24:0:0";
        Date date = df.parse(dateString);
        long s=date.getTime();
        return (s/1000);
	}
	/**通过时间戳获得日期
	 * return Map{"year","month","day","week"}
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Map<String,Object> getWeek_Millis(int time) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Long l = new Long(time+"000");  
	    String d = df.format(l);  
        Date date = df.parse(d);
        Map<String,Object> map = new HashMap<>();
        map.put("year", date.getYear()+1900);
        map.put("month", date.getMonth()+1);
        map.put("day", date.getDate());
        map.put("week", weeks[date.getDay()]);
        return map;
	}
	/**通过时间字符串获得日期(YYYY/MM/DD HH:mm:ss)
	 * return Map{"year","month","day","week"}
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Map<String,Object> getWeek_Millis(String time) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = df.parse(time);
        Map<String,Object> map = new HashMap<>();
        map.put("year", date.getYear()+1900);
        map.put("month", date.getMonth()+1);
        map.put("day", date.getDate());
        map.put("week", weeks[date.getDay()]);
        return map;
	}
	
	public static void main(String []args) throws ParseException{
		
        System.out.println(getWeek_Millis(1479031200).toString());
        
	}
}
