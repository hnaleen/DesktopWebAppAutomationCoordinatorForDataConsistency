package se.nova.auto.dto;

import java.util.Properties;

public class Start
{
  String type;

  String date;

  String time;

  public Start()
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

  public String getDate()
  {
    return date;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getTime()
  {
    return time;
  }

  public void setTime(String time)
  {
    this.time = time;
  }

  public void toProperties(Properties properties)
  {
    properties.setProperty("start_type", type);
    properties.setProperty("start_date", date);
    properties.setProperty("start_time", time);
  }

}
