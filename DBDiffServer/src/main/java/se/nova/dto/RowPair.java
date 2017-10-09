package se.nova.dto;

import java.util.ArrayList;
import java.util.List;

public class RowPair
{
  String primaryKeyPair;
  
  String cosmicPrimaryKey;
  
  String novaPrimaryKey;
  
  List<ColumnValuePair> columnValuePairs = new ArrayList<ColumnValuePair>();

  public RowPair(String primaryKeyPair)
  {
    this.primaryKeyPair = primaryKeyPair;
    cosmicPrimaryKey = primaryKeyPair.split(",")[1];
    novaPrimaryKey = primaryKeyPair.split(",")[0];
  }
  
  public ColumnValuePair getColumnValuesPairByName(String columnName)
  {
    for (ColumnValuePair columnValuePair : columnValuePairs)
    {
      if (columnValuePair.getColumnName().equals(columnName))
      {
        return columnValuePair;
      }
    }
    
    ColumnValuePair columnValuePair = new ColumnValuePair(columnName);
    columnValuePairs.add(columnValuePair);
    return columnValuePair;
  }
  
  public String getPrimaryKeyPair()
  {
    return primaryKeyPair;
  }

  public String getCosmicPrimaryKey()
  {
    return cosmicPrimaryKey;
  }

  public String getNovaPrimaryKey()
  {
    return novaPrimaryKey;
  }

  public List<ColumnValuePair> getColumnValuesPair()
  {
    return columnValuePairs;
  }
  
  @Override
  public String toString()
  {
    String str = "Cosmic Key : " + cosmicPrimaryKey + " | Nova Key : " + novaPrimaryKey + "\n" ;
    for (ColumnValuePair columnValuePair : columnValuePairs)
    {
      str = str + columnValuePair.toString() + "\n";
    }
    return str;
  }
  
  public RowPair()
  {
  }
}

