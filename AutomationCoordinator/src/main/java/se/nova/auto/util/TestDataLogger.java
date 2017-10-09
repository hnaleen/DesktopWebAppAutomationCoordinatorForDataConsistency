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

public class TestDataLogger
{
  File testSuiteResultFolder;

  Logger logger = Logger.getLogger("DiffApp");

  ArgumentProcessor argumentProcessor;

  public TestDataLogger(ArgumentProcessor argumentProcessor)
  {
    this.argumentProcessor = argumentProcessor;
    setupTestSuiteFolder();
    initLogger();
  }

  private void setupTestSuiteFolder()
  {
    testSuiteResultFolder = new File(argumentProcessor.getTestResultDir() + "\\" + getTestSuiteName());
    testSuiteResultFolder.mkdir();
  }

  public void logTestSuiteStart(int numberOfTestCases)
  {
    logger.info("Execution of Test Suite Started (Test Case Count: " + numberOfTestCases + ") \n");
  }

  public void logTestSuiteFinish()
  {
    logger.info("Test Suite Completed");
  }

  public void logTestCaseStart(TestRunner testRunner, TestData testData)
  {
    logger.info("Executing Test Case ID :" + testData.getTestCaseId() + " in " + testRunner.getName());
  }

  public void logTestCaseFinish(TestRunner testRunner, TestData testData, TestResult testResult)
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
          new FileWriter(testSuiteResultFolder.getPath() + "\\" + testData.getTestCaseId() + ".json", true));
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
      String logFileName = testSuiteResultFolder.getPath() + "/TestSuite.log";
      logger.addAppender(
          new RollingFileAppender(new PatternLayout("%d{yyyy-MMM-dd HH:mm:ss,SSS} %x[%-5p] %m%n"), logFileName, true));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private String getTestSuiteName()
  {
    return new SimpleDateFormat("yyyy.MM.dd_hh-mm").format(new Date());
  }

  public void logException(Exception e)
  {
	logger.error(e.getMessage() + " " + e.getStackTrace().toString());
  }
}
