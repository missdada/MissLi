package cn.manytag.manytagUtil.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpDownload {

	/**
	 * 现在网络文件
	 * 
	 * @param urlStr 网络url
	 * @param path 保存地址
	 * @return 0 成功，1网络问题下载失败
	 */
	public static int downLoad(String urlStr, String path) {
		try {
			URL url = new URL(urlStr);

			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(path);
			byte[] buffer = new byte[20 * 1024];
			int read;

			while ((read = is.read(buffer)) > 0) {
				fos.write(buffer, 0, read);
			}

			fos.close();
			is.close();

		} catch (IOException e) {
			return 1;
		}

		return 0;
	}
}
