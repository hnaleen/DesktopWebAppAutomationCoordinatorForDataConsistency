package se.nova.auto.test.suite;

import se.nova.auto.dto.Phase1TestData;
import se.nova.auto.dto.TestData;

public class Phase1TestSuite extends TestSuite
{
  @Override
  public String getName()
  {
    return "Phase1";
  }

  @Override
  public Class<? extends TestData> getTestDataClass()
  {
    return Phase1TestData.class;
  }
  
  @Override
  public String getCosmicAFTBootstrapScript()
  {
    return "tempDevMode";
  }
  
  @Override
  public String getCosmicAutomationTestSuiteScript()
  {
    return "nova_Phase_01";
  }

  @Override
  public String getNovaAutomationTestSuiteScript()
  {
    return "cps01";
  }
  
  @Override
  public String getNovaAFTBootstrapScript()
  {
    return "target/cambio-stella-physician-standard/ConfigSuites.js";
  }

}
