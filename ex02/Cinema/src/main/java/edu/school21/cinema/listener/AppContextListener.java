package edu.school21.cinema.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import edu.school21.cinema.config.ApplicationConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		sce.getServletContext().setAttribute("springContext", context);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
