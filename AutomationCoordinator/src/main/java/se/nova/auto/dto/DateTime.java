package se.nova.auto.dto;

import java.util.Properties;

public class DateTime
{
  String date;

  String time;

  public DateTime()
  {
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
    properties.setProperty("end_dateTime_date", date);
    properties.setProperty("end_dateTime_time", time);
  }

}
