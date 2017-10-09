package se.nova.auto.util;

public class ArgumentProcessor
{
  private String testDataDir;

  private String testScriptDir;

  private String testScript;
  
  private String novaTestRunnerUrl;
  
  private String testSuiteId;
  
  private String local;
  
  private String diffUrl;
  
  private String testResultDir;

  private String novaUrl;

  public ArgumentProcessor(String args[])
  {
    testDataDir = getValueByKey("-testDataDir=", args);
    testScriptDir = getValueByKey("-testScriptDir=", args);
    testScript = getValueByKey("-testScript=", args);
    novaTestRunnerUrl = getValueByKey("-novaTestRunnerUrl=", args);
    testSuiteId = getValueByKey("-testSuiteId=", args);
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

  public String getTestScript()
  {
    return testScript;
  }
  
  public String getNovaTestRunnerUrl()
  {
    return novaTestRunnerUrl;
  }
  
  public String getTestSuiteId()
  {
    return testSuiteId;
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
