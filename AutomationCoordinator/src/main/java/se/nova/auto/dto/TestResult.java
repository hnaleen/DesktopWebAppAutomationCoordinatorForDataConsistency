package se.nova.auto.dto;

public enum TestResult
{
  SUCESS, FAILURE, TIME_OUT;
  
  public boolean isSuccessful()
  {
    return this == TestResult.SUCESS;
  }
}
