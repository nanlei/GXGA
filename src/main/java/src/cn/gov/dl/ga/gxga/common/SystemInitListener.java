package cn.gov.dl.ga.gxga.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统初始化监听器，加载系统缓存等
 * 
 * @author Nan Lei
 * 
 */
public class SystemInitListener implements ServletContextListener {
	private Logger logger = LoggerFactory.getLogger(SystemInitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			// Init System Cache in Memory
			SystemCache.getInstants().init(event.getServletContext());

		} catch (Exception e) {
			logger.error("{}", ExceptionUtils.getStackTrace(e));
		}
	}
}
