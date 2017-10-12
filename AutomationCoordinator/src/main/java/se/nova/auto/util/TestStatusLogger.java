package se.nova.auto.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;
import se.nova.auto.test.runner.CosmicTestRunner;
import se.nova.auto.test.runner.TestRunner;
import se.nova.auto.test.suite.TestSuite;

public class TestStatusLogger
{
  File testResultMainFolder;

  File currentTestSuiteResultFolder;

  Logger logger = Logger.getLogger("DiffApp");

  ArgumentProcessor argumentProcessor = ArgumentProcessor.getInstance();

  public TestStatusLogger()
  {
    setupTestResultsMainFolder();
    initLogger();
  }

  private void setupTestResultsMainFolder()
  {
    testResultMainFolder = new File(argumentProcessor.getTestResultDir() + "\\" + getTestResultFolderName());
    testResultMainFolder.mkdir();
  }
  
  public void logTestSuiteStart(TestSuite testSuite, boolean isFirstAttempt)
  {
    if (isFirstAttempt)
    {
      logTestSuiteStart(testSuite);
    }
    else
    {
      logTestSuiteRetryStart(testSuite);
    }
  }

  private void logTestSuiteStart(TestSuite testSuite)
  {
    setupTestSuiteResultFolder(testSuite);
    logger.info("Execution of Test Suite : [" + testSuite.getName() + "] Started (Test Case Count: "
        + testSuite.getTestDataForAllTestCases().size() + ") \n");
  }
  
  private void logTestSuiteRetryStart(TestSuite testSuite)
  {
    logger.info("Re-Execution of Test Suite : [" + testSuite.getName() + "] (Test Case Count: "
        + testSuite.getFailedTestDataForRetry().size() + ") \n");
  }

  private void setupTestSuiteResultFolder(TestSuite testSuite)
  {
    currentTestSuiteResultFolder = new File(testResultMainFolder.getPath() + "\\" + testSuite.getName());
    currentTestSuiteResultFolder.mkdir();
  }
  
  public void logTestSuiteFinish(TestSuite testSuite, boolean isFirstAttempt)
  {
    if(isFirstAttempt)
    {
      logTestSuiteFinish(testSuite);
    }
    else
    {
      logTestSuiteRetryFinish(testSuite);
    }
  }

  private void logTestSuiteFinish(TestSuite testSuite)
  {
    logger.info("Execution of Test Suite : [" + testSuite.getName() + "]  Completed.");
  }
  
  private void logTestSuiteRetryFinish(TestSuite testSuite)
  {
    logger.info("Re-Execution of Test Suite : [" + testSuite.getName() + "]  Completed.");
  }

  public void logTestCaseStart(TestRunner testRunner, TestData testData, boolean isFirstAttempt)
  {
    String msg = "Executing Test Case ID :" + testData.getTestCaseId() + " in " + testRunner.getName();
    if (!isFirstAttempt) 
    {
      msg += "Re-";
    }
    logger.info(msg);
  }

  public void logTestCaseFinish(TestRunner testRunner, TestData testData, TestResult testResult, boolean isFirstAttempt)
  {
    if (testResult.isSuccessful())
    {
      logger
          .info("Test Case ID :" + testData.getTestCaseId() + " executed in " + testRunner.getName() + " sucessfully");
    }
    else
    {
      logger.error("Execution of Test Case ID :" + testData.getTestCaseId() + " failed in " + testRunner.getName());
      if (isFailureInCosmic(testRunner))
      {
        logger.error("Execution of Test Case ID :" + testData.getTestCaseId() + " will be skipped in Nova");
      }
      if (argumentProcessor.runInLocalMode())
      {
        logger.error("Diff Calculation for Test Case ID :" + testData.getTestCaseId() + " will be skipped\n");
      }
    }
  }

  private boolean isFailureInCosmic(TestRunner testRunner)
  {
    return testRunner instanceof CosmicTestRunner;
  }

  public void logDiffStart(TestData testData)
  {
    logger.info("Starting Diff calculation for Test Case ID :" + testData.getTestCaseId());
  }

  public void logDiffResult(TestData testData, String diffInJson)
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(
          new FileWriter(currentTestSuiteResultFolder.getPath() + "\\" + testData.getTestCaseId() + ".json", true));
      writer.append(diffInJson);
      writer.close();
      logger.info("Diff calculation for Test Case ID :" + testData.getTestCaseId() + " finished sucessfully\n");
    }
    catch (IOException e)
    {
      e.printStackTrace();
      logger.error("Diff calculation for Test Case ID :" + testData.getTestCaseId() + " failed\n");
    }
  }

  private void initLogger()
  {
    try
    {
      String logFileName = testResultMainFolder.getPath() + "/TestSuite.log";
      logger.addAppender(
          new RollingFileAppender(new PatternLayout("%d{yyyy-MMM-dd HH:mm:ss,SSS} %x[%-5p] %m%n"), logFileName, true));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private String getTestResultFolderName()
  {
    return new SimpleDateFormat("yyyy.MM.dd_hh.mma").format(new Date());
  }

  public void logException(Exception e)
  {
    logger.error(e.getMessage() + " " + e.getStackTrace().toString());
  }
}
