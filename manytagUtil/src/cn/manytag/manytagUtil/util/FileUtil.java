package cn.manytag.manytagUtil.util;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class FileUtil {
	public static final Logger log = Logger.getLogger(FileUtil.class);
	private static final int BUFFER_LENGTH = 10240;
	/**
	 * windows 和 linux 通用的文件路径分隔符
	 */
	public static final String fileSeparator = "/";
	private String rootPath;

	public FileUtil(String path) {
		this.rootPath = path;
	}

	/**
	 * 获取修改时间
	 */
	public static Date getModifiedTime(File file) {
		return new Date(file.lastModified());
	}

	/**
	 * 应用于方正框架上传
	 * 
	 * @param request
	 * @param target 目标文件
	 * @return
	 * @throws FileUploadException
	 */
	public Map<String, Object> uploadFile(HttpServletRequest request, File target) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String mimeType = item.getContentType();
					if (mimeType != null) {
						return saveFileItem(item, target);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 应用于方正框架上传
	 * 
	 * @param request
	 * @param modeType 文件模块名称
	 * @return
	 * @throws FileUploadException
	 */
	public Map<String, Object> uploadFile(HttpServletRequest request, String modeType) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String mimeType = item.getContentType();
					if (mimeType != null) {
						return saveFileItem(item, modeType);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 用于图片上传，生成临时文件，生成特定文件名及路径的图片（可能无法生成指定的路径）
	 * 
	 * @param file 源文件
	 * @param modeType
	 * @return
	 */
	public Map<String, Object> saveFileItem(FileItem file, String modeType) {
		int status = 0; //-1:错误，0:初始状态，2:文件已完成传输并重命名为新文件名
		String errorMsg = ""; //status=-1时的错误信息
		long uploadedSize = 0; //上传的文件大小
		String saveName = ""; //status=2时的文件保存名，不含路径
		String savePath = "";//保存路径
		if (file.getSize() != 0) {
			InputStream in = null;
			OutputStream out = null;
			String token = "";
			token = generateToken(file.getName(), String.valueOf(file.getSize()), null);
			File f = null;
			try {
				in = file.getInputStream();
				f = getTokenedFile(token);
				out = new FileOutputStream(f);
				int read = 0;
				final byte[] bytes = new byte[BUFFER_LENGTH];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				uploadedSize = f.length();
				status = 1;
			} catch (IOException e) {
				//页面手动刷新可能导致输入流可能被强制中断
				status = -1;
				errorMsg = "Error: " + e.getLocalizedMessage();
				log.error(errorMsg, e);
			} finally {
				close(in);
				close(out);
			}
			if (status == 1) {//上传成功
				saveName = generateNewSaveNameByToken(token);
				try {
					savePath = saveFile(f, saveName, modeType);
				} catch (IOException e) {
				}
				status = 2;
				log.warn("Token: `" + token + "`, NewName: `" + saveName + "`");
			}
		} else {
			errorMsg = "上传文件为空";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("errorMsg", errorMsg);
		map.put("uploadedSize", uploadedSize);
		map.put("saveName", saveName);
		map.put("savePath", savePath.replace(getFileRepository(), ""));//相对路径
		map.put("name", file.getName());
		return map;
	}

	/**
	 * 用于图片上传，生成临时文件，生成特定文件名及路径的图片
	 * 
	 * @param file 源文件
	 * @param modeType
	 * @return
	 */
	public static Map<String, Object> saveFileItem(FileItem file, File target) {
		int status = 0; //-1:错误，0:初始状态，2:文件已完成传输并重命名为新文件名
		String errorMsg = ""; //status=-1时的错误信息
		long uploadedSize = 0; //上传的文件大小
		String saveName = ""; //status=2时的文件保存名，不含路径
		String savePath = "";//保存路径
		if (file.getSize() != 0) {
			InputStream in = null;
			OutputStream out = null;
			File f = target;
			try {
				in = file.getInputStream();
				out = new FileOutputStream(f);
				int read = 0;
				final byte[] bytes = new byte[BUFFER_LENGTH];
				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				uploadedSize = f.length();
				saveName = target.getName();
				savePath = target.getAbsolutePath();
				status = 2;
			} catch (IOException e) {
				//页面手动刷新可能导致输入流可能被强制中断
				status = -1;
				errorMsg = "Error: " + e.getLocalizedMessage();
				log.error(errorMsg);
				e.printStackTrace();
			} finally {
				close(in);
				close(out);
			}
		} else {
			errorMsg = "上传文件为空";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("errorMsg", errorMsg);
		map.put("uploadedSize", uploadedSize);
		map.put("saveName", saveName);
		map.put("savePath", savePath);
		map.put("name", file.getName());
		return map;
	}

	/**
	 * 根据临时文件获取文件类型
	 * 
	 * @param token
	 * @return
	 */
	public static String parseFileKind(String token) {
		String extName = token.substring(token.lastIndexOf(".") + 1).toLowerCase();
		if ("jpg".equals(extName) || "jpeg".equals(extName) || "png".equals(extName)) {
			return "image";
		} else if ("mp4".equals(extName)) {
			return "video";
		} else {
			return "file";
		}
	}

	/**
	 * 获取文件保存具体路径：文件仓库跟目录/文件种类目录(如video、image)/8位年月日 文件种类目录根据文件扩展名自动创建
	 * 日期目录根据当前时间自动创建
	 * 
	 * @deprecated 因为无法合理的提示创建文件夹可能存在的问题请使用getFullSavePath2
	 * @return
	 */
	public String getFullSavePath(String saveName, String modeType) {
		//获取文件保存目录
		String savePath = getFileRepository() + fileSeparator + modeType;
		//获取当前年月日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		//获取文件种类
		String fileKind = parseFileKind(saveName);
		//拼装文件全路径
		String fullSavePath = savePath + fileSeparator + fileKind + fileSeparator + ymd;
		//检查并创建日期目录
		File dirFile = new File(fullSavePath);
		if (dirFile.exists() && !dirFile.isDirectory()) {
			throw new RuntimeException(fullSavePath + "路径被文件占用");
		}

		if (!dirFile.exists()) {
			//不存在则尝试创建目录
			if (!dirFile.mkdirs()) {
				if (!dirFile.exists()) {
					throw new RuntimeException(fullSavePath + " folder create exception");
				}
			}
		}
		log.info("[getFullSavePath]" + fullSavePath);
		return fullSavePath;
	}

	public String getFullSavePath2(String saveName, String modeType) throws IOException {
		//获取文件保存目录
		String savePath = getFileRepository() + fileSeparator + modeType;
		//获取当前年月日
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		//获取文件种类
		String fileKind = parseFileKind(saveName);
		//拼装文件全路径
		String fullSavePath = savePath + fileSeparator + fileKind + fileSeparator + ymd;
		//检查并创建日期目录
		File dirFile = new File(fullSavePath);
		if (dirFile.exists() && !dirFile.isDirectory()) {
			throw new IOException(fullSavePath + "路径被文件占用");
		}

		if (!dirFile.exists()) {
			//不存在则尝试创建目录
			if (!dirFile.mkdirs()) {
				if (!dirFile.exists()) {
					throw new IOException(fullSavePath + " folder create exception");
				}
			}
		}
		log.info("[getFullSavePath]" + fullSavePath);
		return fullSavePath;
	}

	/**
	 * 获取根目录
	 * 
	 * @return
	 */
	public String getFileRepository() {
		return this.rootPath;
	}

	/**
	 * 生成Token,即临时文件名称 正常情况:TEMP_ 文件最后修改时间 + "_" + size的值 + 原扩展名
	 * modified=null的情况:TEMP_ name.HASHCODE + "_" + size的值 + 原扩展名
	 * size和modified一样，则生成的token一样
	 * 
	 * @param name 文件名
	 * @param size 文件大小
	 * @param modified 文件最后修改时间
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String name, String size, String modified) {
		if (size == null) {
			return null;
		}
		String token = "TEMP_";
		if (modified == null) {
			int code = name.hashCode();
			token += (code > 0 ? "A" : "B") + Math.abs(code);
		} else {
			token += modified;
		}
		token += "_" + size.trim() + getExtName(name);
		return token;
	}

	/**
	 * 根据文件名返回文件扩展名,小写,包含小数点
	 */
	public static String getExtName(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
	}

	public File getTokenedFile(String token) throws IOException {
		log.warn("[getTokenedFile]" + getFileRepository() + fileSeparator + token);
		File f = new File(getFileRepository() + fileSeparator + token);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	/**
	 * 根据Token生成新的文件保存名，不含路径 - 采用闲乐图片命名法
	 * 
	 * @param token
	 * @return
	 */
	public String generateNewSaveNameByToken(String token) {
		String fileNameSuffix = "";
		//图片文件获取图片的尺寸信息
		if (parseFileKind(token).equals("image")) {
			File imageFile = new File(getFileRepository() + fileSeparator + token);
			//生成图片尺寸后缀(图片宽度x图片高度.扩展名)
			try {
				BufferedImage bimg = ImageIO.read(imageFile);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				fileNameSuffix = width + "x" + height + getExtName(token);
			} catch (IOException e) {
				//获取图片信息失败，则按照原来的方式命名
				fileNameSuffix = 430 + "x" + 300 + getExtName(token);
				log.error("Error:" + e.getLocalizedMessage());
			}
		} else {
			//直接截取toke的后缀(文件大小.扩展名)
			fileNameSuffix = token.substring(token.lastIndexOf("_") + 1);
		}
		//根据上传完成的系统时间生成新的文件名
		String fileName = n2s(getNowTimestamp()) + "_!!" + fileNameSuffix;
		return fileName;
	}

	public String saveFile(File file, String newSaveName, String modeType) throws IOException {
		// TODO 获取扩展名,考虑采用扩展名建文件夹分类保存
		String fullFileName = getFullSavePath2(newSaveName, modeType) + fileSeparator + newSaveName;
		//若文件存在则重新生成
		if (file.exists()) {
			File dst = new File(fullFileName);
			file.renameTo(dst);
		}
		return fullFileName;
	}

	/**
	 * 路径为文件且不为空则进行删除
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取当前时间的时间戳，单位秒
	 * 
	 * @return
	 */
	public static int getNowTimestamp() {
		Long time = System.currentTimeMillis() / 1000;
		return time.intValue();
	}

	/**
	 * 把10位时间戳数字转成字符串(混合大小写)
	 * 
	 * @param num
	 * @return
	 */
	public static String n2s(int num) {
		String str = "";
		char _char;
		String numStr = String.valueOf(num);
		for (int i = 0; i < numStr.length(); i++) {
			int n = Integer.parseInt(numStr.substring(i, i + 1));
			if (n <= 2) { //这里最后一组只保留xyz不转换，用来做分隔符
				_char = (char) (n + 65 + rand(0, 3) * 10 + rand(0, 2) * 32);
			} else {
				_char = (char) (n + 65 + rand(0, 2) * 10 + rand(0, 2) * 32);
			}
			str += _char;
		}
		return str;
	}

	/**
	 * 产生随机整数,范围[m,n)
	 * 
	 * @return
	 */
	private static int rand(int m, int n) {
		return (int) (Math.random() * (n - m) + m);
	}

	/**
	 * close the IO stream.
	 * 
	 * @param stream
	 */
	public static void close(Closeable stream) {
		try {
			if (stream != null)
				stream.close();
		} catch (IOException e) {
		}
	}
}