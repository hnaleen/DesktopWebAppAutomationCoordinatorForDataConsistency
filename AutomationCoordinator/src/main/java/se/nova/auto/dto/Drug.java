package se.nova.auto.dto;

import java.util.Properties;

public class Drug
{
  String action;

  Start start;

  public Drug()
  {
  }

  public String getAction()
  {
    return action;
  }

  public Start getStart()
  {
    return start;
  }

  public void setAction(String action)
  {
    this.action = action;
  }

  public void setStart(Start start)
  {
    this.start = start;
  }

  public void toProperties(Properties properties)
  {
    properties.setProperty("action", action);
    start.toProperties(properties);
  }

}
