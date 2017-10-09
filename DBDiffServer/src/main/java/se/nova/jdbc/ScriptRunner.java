package se.nova.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import se.nova.Constants;
import se.nova.dto.DBRow;
import se.nova.dto.Diff;
import se.nova.util.FileUtil;

public class ScriptRunner
{
  private FileUtil fileUtil;

  private DataSource dataSource;

  public ScriptRunner(DataSource dataSource, FileUtil fileUtil)
  {
    this.dataSource = dataSource;
    this.fileUtil = fileUtil;
  }

  public Diff compareLatestRecordsOfUser(String userId)
  {
    Diff diff = null;
    try
    {
      String genericScript = fileUtil.getDBScript();
      String customizedScriptForUser = customizeScriptForUser(genericScript, userId);
      List<DBRow> dbRows = executeScript(customizedScriptForUser);
      diff = new Diff(dbRows);
    }
    catch (Exception e)
    {
      diff = diff == null ? new Diff() : diff;
      diff.setContainsErrors(true);
      e.printStackTrace();
    }
    return diff;
  }

  private List<DBRow> executeScript(String script)
  {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    List<DBRow> dbDiffRows = jdbcTemplate.query(script, new RowMapper<DBRow>()
    {
      public DBRow mapRow(ResultSet resultSet, int rowNumber) throws SQLException
      {
        String tableName = resultSet.getString("TABLE_NAME");
        String primaryKeyPair = resultSet.getString("PRIMARYKEY").split(">")[1];
        String columnName = resultSet.getString("COLUMN_NAME");
        String value = resultSet.getString("VALUES_MISMATCH");
        return new DBRow(tableName, primaryKeyPair, columnName, value);
      }
    });
    return dbDiffRows;
  }

  private String customizeScriptForUser(String genericScript, String userId)
  {
    String genericEnvProperties = fileUtil.readTemplateEnvPropertiesFile(Constants.ENV_FILE_NAME);
    String customizeEnvPropertiesForUser = genericEnvProperties.replaceAll(Constants.DATA_CREATOR_PLACEHOLDER, userId);
    String customizedScriptForUser = genericScript.replace(Constants.SCRIPT_PARAMTER_PLACEHOLDER,
        customizeEnvPropertiesForUser);
    return customizedScriptForUser;
  }

}
