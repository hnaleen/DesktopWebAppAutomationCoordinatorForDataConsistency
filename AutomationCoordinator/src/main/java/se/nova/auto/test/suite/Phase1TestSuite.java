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
  public String getCosmicTestSuiteScriptName()
  {
    return "tempDevMode";
  }

  @Override
  public String getNovaTestSuiteScriptName()
  {
    return null;
  }
}
