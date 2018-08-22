package cn.manytag.manytagUtil.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.manytag.manytagUtil.util.DateUtils;
import cn.manytag.manytagUtil.util.FileUtil;
import cn.manytag.manytagUtil.util.UuidUtil;

public class Servlet {
	public static final Logger log = Logger.getLogger(Servlet.class);

	/**
	 * 获得url
	 * 
	 * @param request
	 * @return
	 */
	public static String getProjectPath(HttpServletRequest request) {
		return request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getSuffix(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int index = uri.indexOf('.') + 1;
		if (index > 0) {
			return uri.substring(index);
		}
		return "";
	}

	/**
	 * 完成下载功能<br>
	 * 将文件输出到页面
	 * 
	 * @param response
	 * @param filePath 绝对路径
	 * @throws IOException
	 */
	public static void outFile(HttpServletResponse response, String filePath) throws IOException {
		outFile(response, filePath, null);
	}

	/**
	 * 完成下载功能<br>
	 * 将文件输出到页面
	 * 
	 * @param response
	 * @param filePath 绝对路径
	 * @param fileName 下载显示的文件名
	 * @throws IOException
	 */
	public static void outFile(HttpServletResponse response, String filePath, String fileName) throws IOException {
		outFile(response, new File(filePath), fileName);
	}

	/**
	 * 完成下载功能<br>
	 * 将文件输出到页面
	 * 
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public static void outFile(HttpServletResponse response, File file) throws IOException {
		outFile(response, file, null);
	}

	/**
	 * 完成下载功能<br>
	 * 将文件输出到页面
	 * 
	 * @param response
	 * @param file
	 * @param fileName 下载显示的文件名
	 * @throws IOException
	 */
	public static void outFile(HttpServletResponse response, File file, String fileName) throws IOException {
		InputStream is = new FileInputStream(file);
		if (StringUtils.isBlank(fileName)) {
			fileName = file.getName();
		}
		outFile(response, is, fileName);
		is.close();
	}

	/**
	 * 完成下载功能<br>
	 * 将文件输出到页面
	 * 
	 * @param response
	 * @param is
	 * @param fileName
	 * @throws IOException
	 */
	public static void outFile(HttpServletResponse response, InputStream is, String fileName) throws IOException {
		response.setContentType("application/octet-stream");
		//文件长度（字节）
		response.addHeader("Content-Length", "" + is.available());
		String disposition = "attachment;";
		if (!StringUtils.isBlank(fileName)) {
			disposition += "filename=" + URLEncoder.encode(fileName, "UTF-8");
		}
		//设置Content-Disposition  
		response.setHeader("Content-Disposition", disposition);
		OutputStream os = response.getOutputStream();
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = is.read(b)) != -1) {
			os.write(b, 0, len);
		}
		os.close();
	}

	public static String inFileDate(String url, String savePath) throws IOException {
		String dateStr = DateUtils.getNow(DateUtils.yyyyMMdd);
		return dateStr + FileUtil.fileSeparator + inFile(url, savePath + FileUtil.fileSeparator + dateStr);
	}

	/**
	 * 把网络文件下载网络文件，存放在指定的目录中，返回目录后的路径
	 * 
	 * @param url 网络路径
	 * @param savePath 存储目录
	 * @isSuffix 是否要求文件扩展名
	 * @return 存储目录后的路径
	 * @throws IOException
	 */
	public static String inFile(String urlPath, String savePath) throws IOException {
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		URL url = new URL(urlPath);
		URLConnection conn = url.openConnection();
		String newFile = UuidUtil.getUuid();
		String type = conn.getContentType();
		if (type != null && type.contains("image")) {
			newFile += ".jpg";
		}
		InputStream is = conn.getInputStream();
		File file = new File(savePath + FileUtil.fileSeparator + newFile);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[20 * 1024];
		int read;

		while ((read = is.read(buffer)) > 0) {
			fos.write(buffer, 0, read);
		}

		fos.close();
		is.close();
		return newFile;
	}
}
