package se.nova.auto.test.runner;

import java.util.ArrayList;
import java.util.List;

import se.nova.auto.util.ArgumentProcessor;

public class TestRunnerFactory
{
  private static TestRunnerFactory instance;

  private List<TestRunner> testRunners = new ArrayList<TestRunner>();

  private TestRunnerFactory()
  {
    testRunners.add(new CosmicTestRunner());
    testRunners.add(new NovaTestRunner());
  }

  public static TestRunnerFactory getInstance()
  {
    if (instance == null)
    {
      instance = new TestRunnerFactory();
    }
    return instance;
  }

  public TestRunner getTestRunner(String name)
  {
    TestRunner testRunner = null;
    if (ArgumentProcessor.getInstance().isRunningInDummyMode(name))
    {
      testRunner = new DummyTestRunner(name);
    }
    else
    {
      for (TestRunner runner : testRunners)
      {
        if (runner.getName().equalsIgnoreCase(name))
        {
          testRunner = runner;
          break;
        }
      }
    }
    return testRunner;
  }

}
