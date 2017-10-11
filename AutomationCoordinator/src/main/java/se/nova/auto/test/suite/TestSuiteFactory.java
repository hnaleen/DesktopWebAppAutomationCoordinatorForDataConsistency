package se.nova.auto.test.suite;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.nova.auto.util.ArgumentProcessor;

public class TestSuiteFactory
{
  private static TestSuiteFactory testSuiteFactory = new TestSuiteFactory();

  public static TestSuiteFactory getInstance()
  {
    return testSuiteFactory;
  }
  
  private List<String> getAvailableTestSuiteNames()
  {
    String mainTestDataDirectoryPath = ArgumentProcessor.getTestDataDirectory();
    File mainTestDataDirectory = new File(mainTestDataDirectoryPath);
    String[] testSuiteNames = mainTestDataDirectory.list(new FilenameFilter()
    {
      @Override
      public boolean accept(File file, String name)
      {
        return file.isDirectory();
      }
    });
    return Arrays.asList(testSuiteNames);
  }

  public List<TestSuite> getAllTestSuites()
  {
    List<TestSuite> testSuites = new ArrayList<>();
    List<String> availableTestSuiteNames = getAvailableTestSuiteNames();
    availableTestSuiteNames.forEach(name -> 
    {
      if (TestSuiteRepository.getInstance().isTestSuiteRegistered(name))
      {
        testSuites.add(TestSuiteRepository.getInstance().getTestSuiteByName(name));
      }
    });
    return testSuites;
  }

  private TestSuiteFactory()
  {
  }
}
