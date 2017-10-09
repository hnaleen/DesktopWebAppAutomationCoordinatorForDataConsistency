package net.sourceforge.marathon.junit;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sourceforge.marathon.api.IConsole;

public class CosmicAutomatedDDTestSuite extends TestSuite implements Test //HNA
{
  private Properties testData;

  public CosmicAutomatedDDTestSuite(File file, boolean acceptChecklist, IConsole console, Properties testData)
      throws IOException
  {
    this.testData = testData;
    MarathonTestCase testCase = new MarathonTestCase(file, acceptChecklist, console, testData, getName());
    super.addTest(testCase);
  }

  public String getName()
  {
    return testData.getProperty("testCaseId");
  }

  @Override
  public String toString()
  {
    return getName();
  }

}
