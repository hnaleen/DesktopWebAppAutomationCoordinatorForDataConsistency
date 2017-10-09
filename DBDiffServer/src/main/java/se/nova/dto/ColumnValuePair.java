package se.nova.dto;

public class ColumnValuePair
{
  String columnName;
  
  String cosmicValue;
  
  String novaValue;
  
  boolean cosmicValueSet = false;
  
  boolean novaValueSet = false;
  
  public ColumnValuePair(String columnName)
  {
    this.columnName = columnName;
  }

  public String getColumnName()
  {
    return columnName;
  }

  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }

  public String getCosmicValue()
  {
    return cosmicValue;
  }
  
  public void setCosmicValue(String cosmicValue)
  {
    this.cosmicValue = cosmicValue;
    this.cosmicValueSet = true;
  }
  
  public void setNovaValue(String novaValue)
  {
    this.novaValue = novaValue;
    this.novaValueSet = true;
  }

  public String getNovaValue()
  {
    return novaValue;
  }
  
  @Override
  public String toString()
  {
    return "Column Name : " + columnName + " | Cosmic Value : " + cosmicValue + " | Nova value : " + novaValue;
  }
  
  public boolean isCosmicValueSet()
  {
    return cosmicValueSet;
  }
  
  public boolean isNovaValueSet()
  {
    return novaValueSet;
  }

  public ColumnValuePair()
  {
  }
}
