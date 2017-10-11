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
import se.nova.auto.util.TestStatusLogger;

public class AutomationCoordinator
{
  private ArgumentProcessor argumentProcessor;

  private TestRunner cosmicTestRunner;

  private TestRunner novaTestRunner;

  private TestStatusLogger logger;

  private DBDiffClient diffCalculator;

  public AutomationCoordinator(String[] args)
  {
    argumentProcessor = new ArgumentProcessor(args);
    cosmicTestRunner = new CosmicTestRunner(argumentProcessor);
    novaTestRunner = new NovaTestRunner(argumentProcessor);
    logger = new TestStatusLogger(argumentProcessor);
    diffCalculator = new DBDiffClient(argumentProcessor);
  }

  public static void main(String[] args)
  {
    AutomationCoordinator coordinator = new AutomationCoordinator(args);
    coordinator.run();
    System.exit(0);
  }

  private void run()
  {
    List<TestSuite> testSuites = TestSuiteFactory.getInstance().getAllTestSuites();
    testSuites.forEach(testSuite -> runTestSuite(testSuite));
  }

  private void runTestSuite(TestSuite testSuite)
  {
    List<TestData> allTestData = testSuite.getTestDataForAllTestCases();
    logger.logTestSuiteStart(testSuite);

    for (TestData testData : allTestData)
    {
      if (executeTestCase(cosmicTestRunner, testData, testSuite.getName(), testSuite.getCosmicTestSuiteScriptName())
          .isSuccessful())
      {
        if (executeTestCase(novaTestRunner, testData, testSuite.getName(), testSuite.getNovaTestSuiteScriptName())
            .isSuccessful())
        {
          calculateDiffAndGenerateReport(testData);
        }
      }
    }

    logger.logTestSuiteFinish(testSuite);
  }

  private TestResult executeTestCase(TestRunner testRunner, TestData testData, String testSuiteName,
                                     String testScriptName)
  {
    logger.logTestCaseStart(testRunner, testData);
    TestResult testResult;
    try
    {
      testResult = testRunner.runTest(testData, testScriptName);
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
