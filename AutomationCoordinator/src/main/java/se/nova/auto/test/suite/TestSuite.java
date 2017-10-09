package se.nova.auto.test.suite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import se.nova.auto.dto.TestData;
import se.nova.auto.util.ArgumentProcessor;

public class TestSuite
{
  private ArgumentProcessor argumentProcessor;

  private Class<? extends TestData> testDataClazz;

  public TestSuite(ArgumentProcessor argumentProcessor, Class<? extends TestData> testDataClazz)
  {
    this.argumentProcessor = argumentProcessor;
    this.testDataClazz = testDataClazz;
  }

  public List<TestData> getTestDataForAllTestCases()
  {
    return loadTestDataInFolder(argumentProcessor.getTestDataDirectory());
  }

  private List<TestData> loadTestDataInFolder(String testDataDir)
  {
    List<TestData> allTestData = new ArrayList<TestData>();
    File folder = new File(testDataDir);
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
      JAXBContext jaxbContext = JAXBContext.newInstance(testDataClazz);
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

}
