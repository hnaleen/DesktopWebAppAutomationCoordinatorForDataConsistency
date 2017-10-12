package se.nova.auto.dto;

import java.util.Properties;

public class PGD
{
  String unitName;

  String action;

  public PGD()
  {
  }

  public void setUnitName(String unitName)
  {
    this.unitName = unitName;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public String getUnitName()
  {
    return unitName;
  }

  public String getAction()
  {
    return action;
  }

  public void toProperties(Properties properties)
  {
    properties.setProperty("unitName", unitName);
    properties.setProperty("action", action);
  }
}
