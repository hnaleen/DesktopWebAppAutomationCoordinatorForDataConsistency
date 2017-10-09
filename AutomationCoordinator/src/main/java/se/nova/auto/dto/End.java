package se.nova.auto.dto;

import java.util.Properties;

public class End
{
  String type;

  DateTime dateTime;

  After after;

  public End()
  {
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public DateTime getDateTime()
  {
    return dateTime;
  }

  public void setDateTime(DateTime dateTime)
  {
    this.dateTime = dateTime;
  }

  public After getAfter()
  {
    return after;
  }

  public void setAfter(After after)
  {
    this.after = after;
  }

  public void toProperties(Properties properties)
  {
    properties.setProperty("end_type", type);
    if (dateTime != null)
    {
      dateTime.toProperties(properties);
    }
    if (after != null)
    {
      after.toProperties(properties);
    }
  }
}
