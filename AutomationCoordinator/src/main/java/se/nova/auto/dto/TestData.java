package se.nova.auto.dto;

import java.util.Properties;

public interface TestData extends Comparable<TestData>
{
  String getTestCaseId();
  
  String getTestCaseStatus();
  
  Properties toProperties();
  
  String getUserId();
  
  String getUserName();
}
