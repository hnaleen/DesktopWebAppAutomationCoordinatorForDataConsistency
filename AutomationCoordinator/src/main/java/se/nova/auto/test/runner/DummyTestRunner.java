package se.nova.auto.test.runner;

import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;

public class DummyTestRunner implements TestRunner
{
  String name;
  
  public DummyTestRunner(String name)
  {
    this.name = name;
  }

  @Override
  public TestResult runTest(TestData testData, String testFrameworkBootstrapScipt, String testSuiteScript)
  {
    return TestResult.SUCESS;
  }
  
  @Override
  public String getName()
  {
    return name;
  }
}
