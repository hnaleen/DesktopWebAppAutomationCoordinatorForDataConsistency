package se.nova.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.springframework.jdbc.datasource.init.ScriptUtils;

import se.nova.Constants;

public class FileUtil
{
  public String getDBScript()
  {
    String script = null;
    try
    {
      BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(Constants.SCRIPT_FILE_NAME)));
      LineNumberReader fileReader = new LineNumberReader(in);
      script = ScriptUtils.readScript(fileReader, "--", ";");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return script;
  }
  
  public String readTemplateEnvPropertiesFile(String fileName)
  {
    StringBuilder builder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName))))
    {
      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null)
      {
        builder.append(sCurrentLine + "\n");
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return builder.toString();
  }
}
