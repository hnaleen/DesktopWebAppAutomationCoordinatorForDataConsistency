package se.nova.auto;

import java.util.List;

import se.nova.auto.diff.DBDiffClient;
import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;
import se.nova.auto.test.runner.CosmicTestRunner;
import se.nova.auto.test.runner.NovaTestRunner;
import se.nova.auto.test.runner.TestRunner;
import se.nova.auto.test.suite.TestSuite;
import se.nova.auto.test.suite.TestSuiteFactory;
import se.nova.auto.util.ArgumentProcessor;
import se.nova.auto.util.TestDataLogger;

public class AutomationCoordinator
{
  private ArgumentProcessor argumentProcessor;

  private TestRunner cosmicTestRunner;

  private TestRunner novaTestRunner;

  private TestDataLogger logger;

  private DBDiffClient diffCalculator;

  public AutomationCoordinator(String[] args)
  {
    argumentProcessor = new ArgumentProcessor(args);
    cosmicTestRunner = new CosmicTestRunner(argumentProcessor);
    novaTestRunner = new NovaTestRunner(argumentProcessor);
    logger = new TestDataLogger(argumentProcessor);
    diffCalculator = new DBDiffClient(argumentProcessor);
  }

  public static void main(String[] args)
  {
    AutomationCoordinator coordinator = new AutomationCoordinator(args);
    coordinator.runTestSuite();
    System.exit(0);
  }

  private void runTestSuite()
  {
    TestSuite testSuite = TestSuiteFactory.getInstance().create(argumentProcessor, argumentProcessor.getTestSuiteId());
    List<TestData> allTestData = testSuite.getTestDataForAllTestCases();
    logger.logTestSuiteStart(allTestData.size());

    for (TestData testData : allTestData)
    {
//      if (executeTestCase(cosmicTestRunner, testData).isSuccessful())
      {
        if (executeTestCase(novaTestRunner, testData).isSuccessful())
        {
          calculateDiffAndGenerateReport(testData);
        }
      }
    }
    
    logger.logTestSuiteFinish();
  }

  private TestResult executeTestCase(TestRunner testRunner, TestData testData)
  {
    logger.logTestCaseStart(testRunner, testData);
    TestResult testResult;
    try
    {
      testResult = testRunner.runTest(testData);
    }
    catch (Exception e)
    {
      testResult = TestResult.FAILURE;
      logger.logException(e);
    }
    logger.logTestCaseFinish(testRunner, testData, testResult);
    return testResult;
  }

  private void calculateDiffAndGenerateReport(TestData testData)
  {
    if (argumentProcessor.runInLocalMode())
    {
      logger.logDiffStart(testData);
      String diffInJson = diffCalculator.calculateDiffForUser(testData.getUserId());
      logger.logDiffResult(testData, diffInJson);
    }
  }
}
