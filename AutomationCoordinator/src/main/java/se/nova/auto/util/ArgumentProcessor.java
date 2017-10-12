package se.nova.auto.util;

public class ArgumentProcessor
{
  private static ArgumentProcessor instance;

  private String testDataDir;

  private String testScriptDir;

  private String novaTestRunnerUrl;

  private String local;

  private String diffUrl;

  private String testResultDir;

  private String novaUrl;
  
  public static ArgumentProcessor getInstance()
  {
    if (instance == null)
    {
      instance = new ArgumentProcessor();
    }
    return instance;
  }

  public void init(String args[])
  {
    testDataDir = getValueByKey("-testDataDir=", args);
    testScriptDir = getValueByKey("-testScriptDir=", args);
    novaTestRunnerUrl = getValueByKey("-novaTestRunnerUrl=", args);
    local = getValueByKey("-local=", args);
    diffUrl = getValueByKey("-diffUrl=", args);
    testResultDir = getValueByKey("-testResultDir=", args);
    novaUrl = getValueByKey("-novaUrl", args);
  }

  private String getValueByKey(String key, String args[])
  {
    String value = null;
    for (String currentArg : args)
    {
      if (currentArg.startsWith(key))
      {
        value = currentArg.split("=")[1];
        break;
      }
    }
    return value;
  }

  public String getTestDataDirectory()
  {
    return testDataDir;
  }

  public String getTestScriptDirectory()
  {
    return testScriptDir;
  }

  public String getNovaTestRunnerUrl()
  {
    return novaTestRunnerUrl;
  }

  public boolean runInLocalMode()
  {
    return local.equalsIgnoreCase("true");
  }

  public String getDiffUrl()
  {
    return diffUrl;
  }

  public String getTestResultDir()
  {
    return testResultDir;
  }

  public String getNovaUrl()
  {
    return novaUrl;
  }
}
