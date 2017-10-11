package se.nova.auto.test.runner;

import java.util.Properties;

import net.sourceforge.marathon.Main;
import se.nova.auto.dto.TestData;
import se.nova.auto.dto.TestResult;
import se.nova.auto.util.ArgumentProcessor;

public class CosmicTestRunner implements TestRunner
{
  private ArgumentProcessor argumentProcessor;

  Main cosmicAutomator = new Main();

  public CosmicTestRunner(ArgumentProcessor argumentProcessor)
  {
    this.argumentProcessor = argumentProcessor;
  }

  public TestResult runTest(TestData testData, String testScriptName)
  {
    Properties properties = testData.toProperties();
    boolean isTestSucess = cosmicAutomator
        .runCosmicTestCase(argumentProcessor.getTestScriptDirectory(), testScriptName, properties);
    return isTestSucess ? TestResult.SUCESS : TestResult.FAILURE;
  }

  public String getName()
  {
    return "Cosmic";
  }
}
