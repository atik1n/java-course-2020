package me.salieri.Java_Course.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MVCConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return null;
  }

  @Override
  protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
    final DispatcherServlet servlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
    servlet.setThrowExceptionIfNoHandlerFound(true);
    return servlet;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {WebConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
}
