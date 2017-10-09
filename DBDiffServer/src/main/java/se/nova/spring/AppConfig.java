package se.nova.spring;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.nova.jdbc.ScriptRunner;
import se.nova.util.FileUtil;

@Configuration
public class AppConfig
{
  @Resource
  DataSource dataSource;
  
  public AppConfig()
  {
    System.out.println("--------------------------------****************8 called");
  }
  
  @Bean(name="fileUtil")
  public FileUtil getFileUtil()
  {
    return new FileUtil();
  }
  
  @Bean
  public ScriptRunner getScriptRunner()
  {
    return new ScriptRunner(dataSource, getFileUtil());
  }
}
