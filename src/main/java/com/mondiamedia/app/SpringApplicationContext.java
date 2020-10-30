package com.mondiamedia.app;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class SpringApplicationContext implements ApplicationContextAware {
  private static ApplicationContext CONTEXT;

  public static Object getBean(String beanName) {
    return CONTEXT.getBean(beanName);
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException {
    CONTEXT = context;
  }
}
