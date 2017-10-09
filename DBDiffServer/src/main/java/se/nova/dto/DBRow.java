package se.nova.dto;

public class DBRow
{
  String tableName;

  String primaryKeyPair;

  String columnName;

  String value;

  public DBRow(String tableName, String primaryKeyPair, String columnName, String value)
  {
    super();
    this.tableName = tableName;
    this.primaryKeyPair = primaryKeyPair;
    this.columnName = columnName;
    this.value = value;
  }

  public String getTableName()
  {
    return tableName;
  }

  public String getPrimaryKeyPair()
  {
    return primaryKeyPair;
  }

  public String getColumnName()
  {
    return columnName;
  }

  public String getValue()
  {
    return value;
  }
}
