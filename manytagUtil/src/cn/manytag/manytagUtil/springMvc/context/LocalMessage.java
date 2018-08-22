package cn.manytag.manytagUtil.springMvc.context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.manytag.manytagUtil.util.PropertiesUtil;
import cn.manytag.manytagUtil.util.StringUtil;
import cn.manytag.manytagUtil.util.SystemPath;

public class LocalMessage implements ServletContextListener {
	public static final Logger log = Logger.getLogger(LocalMessage.class);
	public static final String SESSION_LOCAL_STR = "sessionLocalStr";
	public static final String LOCAL_FILE_NAME = "config/localConfig.properties";
	public static final String DEFAULT = "default";

	private static final Map<String, Properties> resourceMap = new HashMap<String, Properties>();
	private static String defStr;

	private Properties local;
	private String localStr;

	public LocalMessage() {
		this(defStr);
	}

	public LocalMessage(String localStr) {
		this.local = resourceMap.get(localStr);
		if (local == null) {
			log.error("翻译资源不存在：" + localStr);
		}
		this.localStr = localStr;
	}

	/**
	 * 初始化，加载翻译资源文件
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Properties pro = null;
		String fileName = SystemPath.CLASS_PATH + LOCAL_FILE_NAME;
		try {
			pro = PropertiesUtil.load(fileName);
		} catch (IOException e) {
			log.debug("尝试读取语言配置文件出错：" + e.getMessage() + "\n" + fileName, e);
			return;
		}
		for (Entry<Object, Object> entry : pro.entrySet()) {
			String key = entry.getKey().toString();
			if (DEFAULT.equals(key)) {
				defStr = StringUtil.valueOfNull(entry.getValue());
				continue;
			}
			String file = StringUtil.valueOfNull(entry.getValue());

			Properties p = null;
			try {
				p = PropertiesUtil.loadUtf8(SystemPath.CLASS_PATH + file);
			} catch (IOException e) {
				log.warn("尝试读取语言资源文件出错：" + e.getMessage() + "\n" + file, e);
				continue;
			}
			resourceMap.put(key, p);

		}

		if (defStr == null) {
			log.warn("默认语言获取错误，未设置默认语言。应在 " + LOCAL_FILE_NAME + " 文件中设置 " + DEFAULT + " 项");
		}

		log.info("载入语言资源完毕");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	 * 获取默认语言的LocalMessage
	 * 
	 * @return
	 */
	public static LocalMessage getDefaultMessage() {
		return new LocalMessage(defStr);
	}

	/**
	 * 获取当前session的语言，没有设置语言使用默认语言
	 * 
	 * @return
	 */
	public static LocalMessage getMessage() {
		return getMessage(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession());
	}

	/**
	 * 获取session的语言，没有设置语言使用默认语言
	 * 
	 * @param session
	 * @return
	 */
	public static LocalMessage getMessage(HttpSession session) {
		return new LocalMessage(getSessionLocalStr(session));
	}

	public static String getSessionLocalStr(HttpSession session) {
		String s = (String) session.getAttribute(SESSION_LOCAL_STR);
		if (s == null) {
			if (!setMessage(session, defStr)) {
				log.error("设置翻译失败，翻译资源不存在：" + defStr);
			}
			s = (String) session.getAttribute(SESSION_LOCAL_STR);
		}
		return s;
	}

	/**
	 * 设置session的语言
	 * 
	 * @param session
	 * @param messag 语言标识
	 * @return
	 */
	public static boolean setMessage(HttpSession session, String messag) {
		for (Entry<String, Properties> e : resourceMap.entrySet()) {
			if (e.getKey().equals(messag)) {
				session.setAttribute(SESSION_LOCAL_STR, messag);
				return true;
			}
		}
		log.warn("设置翻译失败，翻译资源不存在：" + defStr);
		return false;
	}

	/**
	 * 获取一个语言字符
	 * 
	 * @param str
	 * @return
	 */
	public String get(String str) {
		Object o = local.get(str);
		if (o == null) {
			log.warn("翻译资源无法找到值，资源标识：" + localStr + " key：" + str);
			return "";
		}
		return o.toString();
	}

	public static String getLocal(String str) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		String localStr = getSessionLocalStr(session);
		Object o = resourceMap.get(localStr).get(str);
		if (o == null) {
			log.warn("翻译资源无法找到值，资源标识：" + localStr + " key：" + str);
			return "";
		}
		return o.toString();
	}

	/**
	 * 获取session的语言标识
	 * 
	 * @param session
	 * @return
	 */
	public static String getLocalStr(HttpSession session) {
		return (String) session.getAttribute(SESSION_LOCAL_STR);
	}

	/**
	 * 获取语言标识
	 * 
	 * @return
	 */
	public String getLocalStr() {
		return localStr;
	}

}
