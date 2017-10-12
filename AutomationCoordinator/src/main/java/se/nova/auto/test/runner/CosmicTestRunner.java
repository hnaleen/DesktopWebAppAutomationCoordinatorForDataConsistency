package se.nova.auto.test.runner;

import java.util.Properties;

import net.sourceforge.marathon.Main;
import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;
import se.nova.auto.util.ArgumentProcessor;

public class CosmicTestRunner implements TestRunner
{
  Main cosmicAutomator = new Main();

  public TestResult runTest(TestData testData, String testFrameworkBootstrapScipt, String testSuiteScript)
  {
    Properties properties = testData.toProperties();
    boolean isTestSucess = cosmicAutomator.runCosmicTestCase(ArgumentProcessor.getInstance().getTestScriptDirectory(),
        testFrameworkBootstrapScipt, testSuiteScript, properties);
    return isTestSucess ? TestResult.SUCESS : TestResult.FAILURE;
  }

  public String getName()
  {
    return "Cosmic";
  }
}
