package se.nova.auto.test.suite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import se.nova.auto.dto.TestData;
import se.nova.auto.util.ArgumentProcessor;

public abstract class TestSuite
{
  private List<TestData> allTestData;
  
  private List<TestData> failedTestDataForRetry = new ArrayList<>();

  public List<TestData> getTestDataForAllTestCases()
  {
    if (allTestData == null) 
    {
      allTestData = loadTestDataInFolder(ArgumentProcessor.getInstance().getTestDataDirectory(), getName());
    }
    return allTestData;
  }
  
  public void addFailedTestCaseForRetry(TestData testCase)
  {
    failedTestDataForRetry.add(testCase);
  }
  
  public List<TestData> getFailedTestDataForRetry()
  {
    return failedTestDataForRetry;
  }

  private List<TestData> loadTestDataInFolder(String baseTestDataDirectory, String testSuiteDirectory)
  {
    List<TestData> allTestData = new ArrayList<TestData>();
    File folder = new File(baseTestDataDirectory + "\\" + testSuiteDirectory);
    for (File file : folder.listFiles())
    {
      if (isXmlFile(file))
      {
        TestData testData = readTestDataFromFile(file);
        if (testData != null)
        {
          allTestData.add(testData);
        }
      }
    }
    Collections.sort(allTestData);
    return allTestData;
  }

  private TestData readTestDataFromFile(File xmlFile)
  {
    TestData testData = null;
    try
    {
      JAXBContext jaxbContext = JAXBContext.newInstance(getTestDataClass());
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      testData = (TestData) jaxbUnmarshaller.unmarshal(xmlFile);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return testData;
  }

  private boolean isXmlFile(File file)
  {
    return file.isFile() && "xml".equalsIgnoreCase(getFileExtension(file));
  }

  private String getFileExtension(File file)
  {
    String fileName = file.getName();
    if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
      return fileName.substring(fileName.lastIndexOf(".") + 1);
    else
      return "";
  }

  public abstract String getName();

  public abstract Class<? extends TestData> getTestDataClass();

  public abstract String getCosmicAutomationTestSuiteScript();

  public abstract String getNovaAutomationTestSuiteScript();

  public abstract String getCosmicAFTBootstrapScript();

  public abstract String getNovaAFTBootstrapScript();

}
