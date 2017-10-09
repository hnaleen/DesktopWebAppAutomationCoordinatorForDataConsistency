package se.nova.dto;

public class Value
{
  enum VALUE_STATUS
  {
    SET, NOT_SET
  };
  
  VALUE_STATUS valueSet = VALUE_STATUS.NOT_SET;

  String value;
  
  public Value()
  {
  }
  
  public void setValue(String value)
  {
    this.value = value;
    valueSet = VALUE_STATUS.SET;
  }
  
  public String getValue()
  {
    return value;
  }
  
  public boolean isValueSet()
  {
    return valueSet == VALUE_STATUS.SET;
  }
  
  public VALUE_STATUS getValueSet()
  {
    return valueSet;
  }
}
