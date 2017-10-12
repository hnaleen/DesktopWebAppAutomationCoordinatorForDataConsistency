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
  private TestRunner cosmicTestRunner;

  private TestRunner novaTestRunner;

  private TestStatusLogger logger;

  private DBDiffClient diffCalculator;

  public AutomationCoordinator(String[] args)
  {
    ArgumentProcessor.getInstance().init(args);
    cosmicTestRunner = new CosmicTestRunner();
    novaTestRunner = new NovaTestRunner();
    logger = new TestStatusLogger();
    diffCalculator = new DBDiffClient();
  }

  public static void main(String[] args)
  {
    new AutomationCoordinator(args).runAllTestSuites();
    System.exit(0);
  }

  private void runAllTestSuites()
  {
    List<TestSuite> testSuites = TestSuiteFactory.getInstance().getAllTestSuites();
    testSuites.forEach(testSuite -> runTestSuite(testSuite));
  }

  private void runTestSuite(TestSuite testSuite)
  {
    runSelectedTestCasesInSuite(testSuite, testSuite.getTestDataForAllTestCases(), true);
    runSelectedTestCasesInSuite(testSuite, testSuite.getFailedTestDataForRetry(), false);
  }

  private void runSelectedTestCasesInSuite(TestSuite testSuite, List<TestData> testDataList, boolean isFirstAttempt)
  {
    logger.logTestSuiteStart(testSuite, isFirstAttempt);
    testDataList.forEach(testData -> runTestCaseInBothClients(testSuite, testData, isFirstAttempt));
    logger.logTestSuiteFinish(testSuite, isFirstAttempt);
  }

  private void runTestCaseInBothClients(TestSuite testSuite, TestData testData, boolean isFirstAttempt)
  {
    if (runTestCase(cosmicTestRunner, testData, testSuite.getName(), testSuite.getCosmicAFTBootstrapScript(),
        testSuite.getCosmicAutomationTestSuiteScript(), isFirstAttempt).isSuccessful())
    {
      if (runTestCase(novaTestRunner, testData, testSuite.getName(), testSuite.getNovaAFTBootstrapScript(),
          testSuite.getNovaAutomationTestSuiteScript(), isFirstAttempt).isSuccessful())
      {
        calculateDiffAndGenerateReport(testData);
      }
    }
    else
    {
      testSuite.addFailedTestCaseForRetry(testData);
    }
  }

  private TestResult runTestCase(TestRunner testRunner, TestData testData, String testSuiteName,
                                 String testFrameworkBootstrapScipt, String testSuiteScript, boolean isFirstAttempt)
  {
    logger.logTestCaseStart(testRunner, testData, isFirstAttempt);
    TestResult testResult;
    try
    {
      testResult = testRunner.runTest(testData, testFrameworkBootstrapScipt, testSuiteScript);
    }
    catch (Exception e)
    {
      testResult = TestResult.FAILURE;
      logger.logException(e);
    }
    logger.logTestCaseFinish(testRunner, testData, testResult, isFirstAttempt);
    return testResult;
  }

  private void calculateDiffAndGenerateReport(TestData testData)
  {
    if (ArgumentProcessor.getInstance().runInLocalMode())
    {
      logger.logDiffStart(testData);
      String diffInJson = diffCalculator.calculateDiffForUser(testData.getUserId());
      logger.logDiffResult(testData, diffInJson);
    }
  }
}
