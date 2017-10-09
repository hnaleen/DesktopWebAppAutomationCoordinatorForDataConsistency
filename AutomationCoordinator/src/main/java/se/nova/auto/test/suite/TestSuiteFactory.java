package se.nova.auto.test.suite;

import se.nova.auto.dto.Phase1TestData;
import se.nova.auto.util.ArgumentProcessor;

public class TestSuiteFactory
{
  private static TestSuiteFactory testSuiteFactory = new TestSuiteFactory();

  public static TestSuiteFactory getInstance()
  {
    return testSuiteFactory;
  }

  public TestSuite create(ArgumentProcessor argumentProcessor, String testSuiteId)
  {
    TestSuite testSuite = null;
    if (testSuiteId.equalsIgnoreCase("1"))
    {
      testSuite = new TestSuite(argumentProcessor, Phase1TestData.class);
    }
    return testSuite;
  }

  private TestSuiteFactory()
  {
  }
}
