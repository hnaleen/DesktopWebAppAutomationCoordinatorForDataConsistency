package se.nova.auto.test.suite;

import java.util.HashMap;
import java.util.Map;

public class TestSuiteRepository
{
  private static TestSuiteRepository instance;
  
  public static TestSuiteRepository getInstance()
  {
    if (instance == null)
    {
      instance = new TestSuiteRepository();
    }
    return instance;
  }
  
  private Map<String, TestSuite> testSuitesByName = new HashMap<>();
  
  public TestSuiteRepository()
  {
    registerTestSuite(new Phase1TestSuite());
    registerTestSuite(new Phase2TestSuite());
  }
  
  private void registerTestSuite(TestSuite testSuite)
  {
    testSuitesByName.put(testSuite.getName(), testSuite);
  }
  
  public boolean isTestSuiteRegistered(String name)
  {
    return testSuitesByName.containsKey(name);
  }
  
  public TestSuite getTestSuiteByName(String name)
  {
    return testSuitesByName.get(name);
  }

}
