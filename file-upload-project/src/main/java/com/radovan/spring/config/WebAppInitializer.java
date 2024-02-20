package com.radovan.spring.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

	//use path from your pc
	private static final String TMP_FOLDER = "C:\\Users\\Administrator\\upload-workspace\\file-upload-project\\src\\main\\resources\\static\\upload\\";
	private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

	@Override
	public void onStartup(ServletContext container) {

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringMvcConfiguration.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("Spring Initializer",
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE,
				MAX_UPLOAD_SIZE * 2L, MAX_UPLOAD_SIZE / 2);

		dispatcher.setMultipartConfig(multipartConfigElement);
	}
}
