package se.nova.dto;

import java.util.ArrayList;
import java.util.List;

public class Diff
{
  List<Table> tables = new ArrayList<Table>();

  boolean containsErrors = false;

  public Diff()
  {
  }

  public Diff(List<DBRow> dbRows)
  {
    for (DBRow dbRow : dbRows)
    {
      markAsDiff(dbRow.getTableName(), dbRow.getPrimaryKeyPair(), dbRow.getColumnName(), dbRow.getValue());
    }
  }

  private void markAsDiff(String tableName, String primaryKeyPair, String columnName, String value)
  {
    ColumnValuePair columnValuesPairByName = getTableByName(tableName).getRowPairByKeys(primaryKeyPair)
        .getColumnValuesPairByName(columnName);
    if (isCosmicPrimaryKeyValid(primaryKeyPair) && !columnValuesPairByName.isCosmicValueSet())
    {
      columnValuesPairByName.setCosmicValue(value);
    }
    else
    {
      columnValuesPairByName.setNovaValue(value);
    }
  }

  private boolean isCosmicPrimaryKeyValid(String primaryKeyPair)
  {
    String primaryKeyPairTrimmed = primaryKeyPair.replace("|", "");
    int cosmicKey = Integer.parseInt(primaryKeyPairTrimmed.split(",")[1]);
    return cosmicKey > 0;
  }

  private Table getTableByName(String name)
  {
    for (Table table : tables)
    {
      if (table.getName().equalsIgnoreCase(name))
      {
        return table;
      }
    }
    Table table = new Table(name);
    tables.add(table);
    return table;
  }

  public void setContainsErrors(boolean containsErrors)
  {
    this.containsErrors = containsErrors;
  }

  public boolean isContainsErrors()
  {
    return containsErrors;
  }

  public List<Table> getTables()
  {
    return tables;
  }
  
  @Override
  public String toString()
  {
    String str = "Full Diff : \n";
    for (Table table : tables)
    {
      str = str + table.toString() + "\n";
    }
    return str;
  }
}
