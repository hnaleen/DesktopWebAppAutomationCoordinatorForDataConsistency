package se.nova.auto.util;

public class ArgumentProcessor
{
  private static String testDataDir;

  private static String testScriptDir;

  private static String novaTestRunnerUrl;
  
  private static String local;
  
  private static String diffUrl;
  
  private static String testResultDir;

  private static String novaUrl;

  public ArgumentProcessor(String args[])
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

  public static String getTestDataDirectory()
  {
    return testDataDir;
  }

  public static String getTestScriptDirectory()
  {
    return testScriptDir;
  }

  public static String getNovaTestRunnerUrl()
  {
    return novaTestRunnerUrl;
  }
  
  public static boolean runInLocalMode()
  {
    return local.equalsIgnoreCase("true");
  }
  
  public static String getDiffUrl()
  {
    return diffUrl;
  }
  
  public static String getTestResultDir()
  {
    return testResultDir;
  }
  
  public static String getNovaUrl()
  {
    return novaUrl;
  }
}
