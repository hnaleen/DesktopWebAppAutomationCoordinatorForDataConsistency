package se.nova.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContext implements ApplicationContextAware
{
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
  {
    AppContext.applicationContext = applicationContext;
  }
  
  public static Object getBean(String beanName)
  {
    return applicationContext.getBean(beanName);
  }
}
