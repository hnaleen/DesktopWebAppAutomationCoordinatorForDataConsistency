package se.nova.auto.test.suite;

import se.nova.auto.dto.Phase2TestData;
import se.nova.auto.dto.TestData;

public class Phase2TestSuite extends TestSuite
{

  @Override
  public String getName()
  {
    return "Phase2";
  }

  @Override
  public Class<? extends TestData> getTestDataClass()
  {
    return Phase2TestData.class;
  }

  @Override
  public String getCosmicAutomationTestSuiteScript()
  {
    return "nova_Phase_02";
  }
  
  @Override
  public String getCosmicAFTBootstrapScript()
  {
    return "tempDevMode";
  }

  @Override
  public String getNovaAutomationTestSuiteScript()
  {
    return "cps02";
  }

  @Override
  public String getNovaAFTBootstrapScript()
  {
    return "target/cambio-stella-physician-standard/ConfigSuites.js";
  }

}
