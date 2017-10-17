package se.nova.auto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import se.nova.auto.test.suite.TestSuite;

public class TestStatusSummarizer
{
  private static TestStatusSummarizer instance = new TestStatusSummarizer();

  private Map<String, Map<String, Status>> statusMap = new HashMap<>();

  public static TestStatusSummarizer getInstance()
  {
    if (instance == null)
    {
      instance = new TestStatusSummarizer();
    }
    return instance;
  }

  public void init(TestSuite testSuite)
  {
    Map<String, Status> testCaseStatusById = new HashMap<>();
    testSuite.getTestDataForAllTestCases()
        .forEach(testCase -> testCaseStatusById.put(testCase.getTestCaseId(), new Status()));
    statusMap.put(testSuite.getName(), testCaseStatusById);
  }

  public void setTestCaseStatus(String testSuiteName, String testCaseId, boolean isRunByCosmic, boolean isSucess)
  {
    Status status = statusMap.get(testSuiteName).get(testCaseId);
    if (isRunByCosmic)
    {
      status.passedInCosmic = isSucess;
    }
    else
    {
      status.passedInNova = isSucess;
    }
  }

  public List<String> getFailedTestCasesInCosmic()
  {
    return getAllFailedTestCases(status -> !status.passedInCosmic);
  }

  public List<String> getFailedTestCasesInNova()
  {
    return getAllFailedTestCases(status -> status.passedInCosmic && !status.passedInNova);
  }

  private List<String> getAllFailedTestCases(Function<Status, Boolean> isFailed)
  {
    List<String> failedTestCaseIds = new ArrayList<>();

    for (Entry<String, Map<String, Status>> entry : statusMap.entrySet())
    {
      String testSuiteName = entry.getKey();
      Map<String, Status> testCaseStatusById = entry.getValue();

      for (Entry<String, Status> entry2 : testCaseStatusById.entrySet())
      {
        Status status = entry2.getValue();
        if (isFailed.apply(status))
        {
          failedTestCaseIds.add("[" + testSuiteName + "]" + entry2.getKey());
        }
      }
    }
    return failedTestCaseIds;
  }

  class Status
  {
    boolean passedInCosmic;

    boolean passedInNova;
  }
}
