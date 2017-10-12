package se.nova.auto.test.runner;

import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;

public interface TestRunner
{
  TestResult runTest(TestData testData, String testFrameworkBootstrapScipt, String testSuiteScript);
  
  String getName();
}
