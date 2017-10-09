package se.nova.dto;

import java.util.ArrayList;
import java.util.List;

public class Table
{
  String name;

  List<RowPair> rowPairs = new ArrayList<RowPair>();

  public Table(String name)
  {
    this.name = name;
  }
  
  public RowPair getRowPairByKeys(String primaryKeyPair)
  {
    for (RowPair rowPair : rowPairs)
    {
      if (rowPair.getPrimaryKeyPair().equals(primaryKeyPair))
      {
        return rowPair;
      }
    }
    
    RowPair rowPair = new RowPair(primaryKeyPair);
    rowPairs.add(rowPair);
    return rowPair;
  }
  
  public String getName()
  {
    return name;
  }

  public List<RowPair> getRowPairs()
  {
    return rowPairs;
  }
  
  @Override
  public String toString()
  {
    String str = "Table : " + name + "\n";
    for (RowPair rowPair : rowPairs)
    {
      str = str + rowPair.toString() + "\n";
    }
    return str;
  }
  
  public Table()
  {
  }
}
