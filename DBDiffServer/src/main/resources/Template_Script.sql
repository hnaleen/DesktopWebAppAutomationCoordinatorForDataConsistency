--#######################################################################################################################
--
--  Name:			MSSQL_ROW_COMPARE.sql
--  Tested on:		MSSQL 2014,MSSQL 2016
--  Description:	This script will compare give rows form listed xml tables
--					you neet to set @environmentPropertiesPath before Execute
--					: Ex: 'C:\Work\BUNI_DM\environment.properties.xml'
--					Note: 
--  Project:		N/A
--	Key:			N/A
--======================================================================================================================
--
--  (c) Copyright Cambio Healthcare Systems AB, 2016.  All rights reserved.
--
--======================================================================================================================
--
--  Change History
--  ==============
--
--  When        Who			What
--  ----------  --------	--------------------------------------------------------------------------------------------
--  2017-07-31  Tharindu.D  Created.
--
--######################################################################################################################


SET NOCOUNT ON




--Use cursor to run upgrade scripts
IF OBJECT_ID('[tempdb].[dbo].[#tblScripts]') IS NOT NULL
BEGIN
	DROP TABLE [dbo].[#tblScripts];
END;
IF OBJECT_ID('tempdb.dbo.#in_char_table') IS NOT NULL BEGIN
	DROP TABLE #in_char_table;	
END
IF OBJECT_ID('tempdb.dbo.#in_char_table1') IS NOT NULL BEGIN
	DROP TABLE #in_char_table1;	
END
IF OBJECT_ID('tempdb.dbo.#tData') IS NOT NULL BEGIN
	DROP TABLE #tData
END
--DECLARE @environmentPropertiesPath	nvarchar(4000)='C:\med\';
--DECLARE	@environmentPropertiesFile	nvarchar(100)='environment.properties.xml';
DECLARE @xml_data					xml='$(XML_TEMPLATE_DATA)';
DECLARE @Cmd						nvarchar(max)=''; 
DECLARE @ParmDefinition				nvarchar(255);

--SET @environmentPropertiesPath	=@environmentPropertiesPath+@environmentPropertiesFile;

--SET @ParmDefinition=N'@LocalOut xml OUTPUT';
--SET @Cmd='SELECT @LocalOut=xCol FROM    (SELECT * FROM OPENROWSET (BULK '''+@environmentPropertiesPath+''', SINGLE_CLOB) AS xCol) AS R(xCol)'; 
--EXEC sp_executesql @Cmd, @ParmDefinition, @LocalOut=@xml_data OUTPUT;

IF OBJECT_ID('tempdb.dbo.#tblScripts') IS NULL BEGIN
	CREATE TABLE #tblScripts(RecordNumber int IDENTITY(1,1) PRIMARY KEY,DATA_CREATOR varchar(128),[STATUS] varchar(1), TABLE_SCHEMA varchar(128),TABLE_NAME varchar(128),EXCLUDE_COLUMNS varchar(8000),PRIMARY_KEY_COLUMN varchar(4000),PRIMARYKEY_COSMIC varchar(8000),PRIMARYKEY_NOVA varchar(8000));
END

IF OBJECT_ID('tempdb.dbo.#in_char_table') IS NULL BEGIN
	CREATE TABLE #in_char_table (RowID int IDENTITY(1,1),[value] [varchar] (900) NOT NULL);
	CREATE UNIQUE CLUSTERED INDEX CIX_in_char_table ON #in_char_table (value);  
END

IF OBJECT_ID('tempdb.dbo.#in_char_table1') IS NULL BEGIN
	CREATE TABLE #in_char_table1 (RowID int IDENTITY(1,1),[value] [varchar] (900) NOT NULL);
	CREATE UNIQUE CLUSTERED INDEX CIX_in_char_table1 ON #in_char_table1 (value);  
END

INSERT INTO #tblScripts(DATA_CREATOR,[STATUS],TABLE_SCHEMA,TABLE_NAME,EXCLUDE_COLUMNS,PRIMARY_KEY_COLUMN,PRIMARYKEY_COSMIC,PRIMARYKEY_NOVA)
SELECT
	 b.c.value('@DATA_CREATOR', 'varchar(128)')						[DATA_CREATOR]
	,f.c.value('@STATUS', 'varchar(1)')								[STATUS]
	,f.c.value('@TABLE_SCHEMA', 'varchar(128)')						[TABLE_SCHEMA]	 
	,f.c.value('@TABLE_NAME', 'varchar(128)')						[TABLE_NAME]
	,f.c.value('EXCLUDE_COLUMNS[1]', 'varchar(128)')				[EXCLUDE_COLUMNS]
	,f.c.value('PRIMARY_KEY_COLUMN[1]', 'varchar(128)')				[PRIMARY_KEY_COLUMN]	
	,f.c.value('PRIMARYKEY_SOURCE[1]', 'varchar(8000)')				[PRIMARYKEY_COSMIC]
	,f.c.value('PRIMARYKEY_DESTINATION[1]', 'varchar(8000)')		[PRIMARYKEY_NOVA]
FROM @xml_data.nodes('Properties/TestCase') b(c)
	CROSS APPLY b.c.nodes('objects') f(c);

--SELECT * FROM #tblScripts

DECLARE @DATA_CREATOR		varchar(128);
DECLARE @tableSchema		varchar(128);
DECLARE @tableName			varchar(128);
DECLARE @excludeColumns		varchar(8000);
DECLARE @primaryKeyColumn	varchar(4000);
DECLARE @primaryKeyCosmic	varchar(8000);
DECLARE @primaryKeyNova		varchar(8000);
DECLARE @PKCosmic			varchar(900);
DECLARE @PKNova				varchar(900);
DECLARE @ssql				nvarchar(max);
DECLARE @SQLString			nvarchar(max);
DECLARE @columnList			varchar(max)='';
DECLARE @validColumns		varchar(max)='';
DECLARE @columnName			varchar(128);
DECLARE @primaryKey_COSMIC	varchar(8000);
DECLARE @primaryKey_NOVA	varchar(8000);
DECLARE @STATUS				varchar(1);

PRINT '[Start]' 
DECLARE curCosmicDB CURSOR READ_ONLY FOR 
SELECT DATA_CREATOR,STATUS,TABLE_SCHEMA,TABLE_NAME ,EXCLUDE_COLUMNS ,PRIMARY_KEY_COLUMN ,PRIMARYKEY_COSMIC,PRIMARYKEY_NOVA FROM #tblScripts ORDER BY RecordNumber;
OPEN curCosmicDB
FETCH NEXT FROM curCosmicDB INTO @DATA_CREATOR,@STATUS,@tableSchema,@tableName ,@excludeColumns ,@primaryKeyColumn ,@primaryKey_COSMIC,@primaryKey_NOVA
WHILE (@@fetch_status = 0)
BEGIN

	SET @ParmDefinition='';
	IF @STATUS='Q' BEGIN		
		SET @SQLString=REPLACE(@primaryKey_COSMIC,'@DATA_CREATOR',@DATA_CREATOR);
		SET @ParmDefinition = N'@primaryKey varchar(8000)='''' OUTPUT';  
		PRINT @SQLString
		EXECUTE sp_executesql @SQLString, @ParmDefinition, @primaryKey=@primaryKeyCosmic OUTPUT; 
		SET @primaryKeyCosmic=SUBSTRING(@primaryKeyCosmic,2,LEN(@primaryKeyCosmic));
		PRINT @primaryKeyCosmic
		
		SET @SQLString=''
		SET @SQLString=REPLACE(@primaryKey_NOVA,'@DATA_CREATOR',@DATA_CREATOR);
		SET @ParmDefinition = N'@primaryKey varchar(8000)='''' OUTPUT';  
		PRINT @SQLString
		EXECUTE sp_executesql @SQLString, @ParmDefinition, @primaryKey=@primaryKeyNova OUTPUT;		
		SET @primaryKeyNova=SUBSTRING(@primaryKeyNova,2,LEN(@primaryKeyNova));
		PRINT @primaryKeyNova
	END
	IF @STATUS='V' BEGIN
		SET @primaryKeyNova=@primaryKey_NOVA;
		SET @primaryKeyCosmic=@primaryKey_COSMIC;
	END
	SET @SQLString='';
	SET @ParmDefinition='';

	PRINT 'Table Schema: '+@tableSchema;
	PRINT 'Table Name: '+@tableName;
	PRINT 'Exclude Columns: '+@excludeColumns;
	PRINT 'First: '+@primaryKeyCosmic
	PRINT 'Second: '+@primaryKeyNova

	SET @SQLString = N'SELECT @columnList=@columnList+'',''+COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_SCHEMA='''+@tableSchema+''') AND (TABLE_NAME='''+@tableName+''') AND (COLUMN_NAME NOT IN('''+REPLACE(@excludeColumns,',',''',''')+'''));';  
	PRINT @SQLString
	SET @ParmDefinition = N'@columnList varchar(8000) OUTPUT';  
	EXECUTE sp_executesql @SQLString, @ParmDefinition, @columnList=@columnList OUTPUT; 
	PRINT 'Column List: '+@columnList
	SET @validColumns=SUBSTRING(@columnList,2,LEN(@columnList));
	PRINT 'Valid Columns: '+@validColumns;  
	PRINT ' '

	IF OBJECT_ID('tempdb.dbo.#tCol') IS NULL BEGIN
		CREATE TABLE #tCol(COLUMN_NAME	varchar(128))
	END
	IF OBJECT_ID('tempdb.dbo.#tData') IS NULL BEGIN
		CREATE TABLE #tData(TABLE_NAME varchar(128),PRIMARYKEY varchar(8000),COLUMN_NAME varchar(128), VALUES_MISMATCH varchar(8000), cnt smallint,ssql nvarchar(max))
	END
	TRUNCATE TABLE #tCol;
	SET @SQLString = N'INSERT INTO #tCol SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_SCHEMA='''+@tableSchema+''') AND (TABLE_NAME='''+@tableName+''') AND (COLUMN_NAME NOT IN('''+REPLACE(@excludeColumns,',',''',''')+''')) ORDER BY ORDINAL_POSITION;';  
	EXECUTE sp_executesql @SQLString; 

	DECLARE curColumnValidate CURSOR FOR 
	SELECT * FROM #tCol
	OPEN curColumnValidate  
	FETCH NEXT FROM curColumnValidate INTO @columnName;

	WHILE @@FETCH_STATUS = 0  BEGIN  
		PRINT @columnName
		
		EXEC [spider3].[charlist_to_table_sp] @primaryKeyCosmic,',';
		EXEC [spider3].[charlist_to_table1_sp] @primaryKeyNova,',';

		DECLARE curIDs CURSOR FOR 
		SELECT ISNULL(A.value,0) primaryKeyCosmic,ISNULL(B.value,0) primaryKeyNova FROM #in_char_table A LEFT JOIN
			  #in_char_table1 B ON A.RowID=B.RowID
		UNION
		SELECT ISNULL(A.value,0) primaryKeyCosmic,ISNULL(B.value,0) primaryKeyNova FROM #in_char_table A RIGHT JOIN
					  #in_char_table1 B ON A.RowID=B.RowID
		OPEN curIDs  
		FETCH NEXT FROM curIDs INTO @PKCosmic,@PKNova;

		WHILE @@FETCH_STATUS = 0  BEGIN 

			SET @ssql=CAST(' INSERT INTO #tData(TABLE_NAME,PRIMARYKEY,COLUMN_NAME,VALUES_MISMATCH,cnt,[ssql])
						SELECT '''+@tableSchema+'.'+@tableName+''' tableName,''<'+@DATA_CREATOR+'>'+@PKCosmic+','+@PKNova+''' primaryKey,'''+@columnName+''' columnName,'+@columnName+',COUNT(*) cnt,''SELECT '+@columnName+' FROM '+@tableSchema+'.'+@tableName+' WHERE '+REPLACE(@primaryKeyColumn,'''','''''')+' IN('''''+@PKCosmic+''''','''''+@PKNova+''''');'' FROM
						(
						SELECT * FROM
						(
							SELECT '+@validColumns+'	FROM '+QUOTENAME(@tableSchema)+'.'+QUOTENAME(@tableName)+'
							  WHERE '+@primaryKeyColumn+' IN('''+@PKCosmic+''') 
							UNION 
							SELECT '+@validColumns+'  FROM '+QUOTENAME(@tableSchema)+'.'+QUOTENAME(@tableName)+'
							  WHERE '+@primaryKeyColumn+' IN('''+@PKNova+''') 
						) T1
						EXCEPT
						SELECT * FROM
						(
							SELECT '+@validColumns+'	FROM '+QUOTENAME(@tableSchema)+'.'+QUOTENAME(@tableName)+'
							  WHERE '+@primaryKeyColumn+' IN('''+@PKCosmic+''') 
							INTERSECT 
							SELECT '+@validColumns+'  FROM '+QUOTENAME(@tableSchema)+'.'+QUOTENAME(@tableName)+'
							  WHERE '+@primaryKeyColumn+' IN('''+@PKNova+''') 
						 ) T2 					  				
						  ) T GROUP BY '+@columnName+' HAVING COUNT(*)=1' AS nvarchar(max));
				
			PRINT DATALENGTH(@ssql);
			PRINT @ssql
			PRINT ' '	
			

			IF LEN(@PKCosmic)>0  AND LEN(@PKNova)>0 BEGIN
				EXECUTE sp_executesql @ssql;
			END

			FETCH NEXT FROM curIDs INTO @PKCosmic,@PKNova;	

		END
		CLOSE curIDs;  
		DEALLOCATE curIDs; 

		TRUNCATE TABLE #in_char_table;	
		TRUNCATE TABLE #in_char_table1;

		FETCH NEXT FROM curColumnValidate INTO @columnName;
	END
	CLOSE curColumnValidate;  
	DEALLOCATE curColumnValidate; 

	SET @tableSchema='';
	SET @tableName='';
	SET @excludeColumns='';
	SET @primaryKeyColumn='';
	SET @ssql='';
	SET @SQLString='';
	SET @columnList='';
	SET @validColumns='';
	SET @columnName='';
	SET @primaryKeyNova='';
	SET @primaryKeyCosmic='';
	SET @STATUS='';

	FETCH NEXT FROM curCosmicDB INTO @DATA_CREATOR,@STATUS,@tableSchema,@tableName ,@excludeColumns ,@primaryKeyColumn ,@primaryKey_COSMIC,@primaryKey_NOVA
END
CLOSE curCosmicDB
DEALLOCATE curCosmicDB
IF OBJECT_ID('tempdb.dbo.#tData') IS NOT NULL BEGIN
	ALTER TABLE #tData DROP COLUMN cnt;
	SELECT * FROM #tData
	DROP TABLE #tData
END
PRINT ' ' 
PRINT ' ' 
PRINT '[End]' 
IF OBJECT_ID('tempdb.dbo.#tblScripts') IS NOT NULL BEGIN
	DROP TABLE #tblScripts
END
IF OBJECT_ID('tempdb.dbo.#in_char_table') IS NOT NULL BEGIN
	DROP TABLE #in_char_table;	
END
IF OBJECT_ID('tempdb.dbo.#in_char_table1') IS NOT NULL BEGIN
	DROP TABLE #in_char_table1;	
END
