package se.nova.auto.dto;

import java.util.Properties;

public class After
{
  String type;

  String length;

  public After()
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

  public String getLength()
  {
    return length;
  }

  public void setLength(String length)
  {
    this.length = length;
  }

  public void toProperties(Properties properties)
  {
    properties.setProperty("end_after_type", type);
    properties.setProperty("end_after_length", length);
  }

}
