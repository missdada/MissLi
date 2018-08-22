package cn.manytag.manytagUtil.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 百度天气API
 * 
 */
public class WeatherApi {

	/**
	 * 返回百度API的天气json
	 * 
	 * @param cityName 城市名
	 * @param ak 百度许可码<br>
	 *            Djl4mOoISYqhnbM1V5nrQ6GXcIGTDo75是我自己申请的一个AK(许可码)，如果访问不了，可以自己去申请一个新的ak
	 *            百度ak申请地址：http://lbsyun.baidu.com/apiconsole/key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String getWeatherInform(String cityName, String ak) throws UnsupportedEncodingException, IOException {
		//百度天气API
		//通过浏览器直接访问http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=Djl4mOoISYqhnbM1V5nrQ6GXcIGTDo75
		//要访问的地址URL，通过URLEncoder.encode()函数对于中文进行转码
		String baiduUrl;
		try {
			baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location=" + URLEncoder.encode(cityName, "utf-8")
					+ "&output=json&ak=Djl4mOoISYqhnbM1V5nrQ6GXcIGTDo75";
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedEncodingException("城市数据不正确：" + cityName + "。" + e.getMessage());
		}

		StringBuffer strBuf = new StringBuffer();

		URL url = new URL(baiduUrl);
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		String line = null;
		while ((line = reader.readLine()) != null)
			strBuf.append(line + " ");
		reader.close();

		return strBuf.toString();
	}

}