package cn.manytag.manytagUtil.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.catalina.loader.WebappClassLoader;
import org.apache.log4j.Logger;

/**
 * 设置线程结束自动销毁
 * 
 */
public class ContextStopThreads implements ServletContextListener {

	private Logger log = Logger.getLogger(ContextStopThreads.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ClassLoader loader = getClass().getClassLoader();
		if (loader instanceof WebappClassLoader) {
			@SuppressWarnings("resource")
			WebappClassLoader webLoader = (WebappClassLoader) loader;
			//设置参数在结束时候自动销毁所有线程
			webLoader.setClearReferencesStopThreads(true);
		} else {
			log.error("getClass().getClassLoader() not is WebappClassLoader，设置线程结束自动销毁失败");
		}
	}

}
