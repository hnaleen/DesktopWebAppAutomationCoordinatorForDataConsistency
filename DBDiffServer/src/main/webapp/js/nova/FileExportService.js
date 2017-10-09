var app = angular.module("diffApp");

app.service('FileExportService', function (FileSaver, Blob) {

    this.export = function (tables) {
        var data = new Blob([covertToCsv(tables)], {type: "text/csv;charset=utf-8"});
        FileSaver.saveAs(data, 'text.csv');
    };

    var covertToCsv = function (tables) {
        var csv = "";
        for (var t = 0; t < tables.length; t++) {
            var table = tables[t];
            for (var r = 0; r < table.rowPairs.length; r++) {
                var row = table.rowPairs[r];
                for (var c = 0; c < row.columnValuesPair.length; c++) {
                    var col = row.columnValuesPair[c];
                    csv = csv + createCsvRow(table.name, row.cosmicPrimaryKey, row.novaPrimaryKey, col.columnName, col.cosmicValueSet, col.cosmicValue);
                    csv = csv + createCsvRow(table.name, row.cosmicPrimaryKey, row.novaPrimaryKey, col.columnName, col.novaValueSet, col.novaValue);
                }
            }
            console.log(csv);
        }
        return csv;
    }

    var createCsvRow = function (tableName, cosmicPrimaryKey, novaPrimaryKey, columnName, isValueSet, value) {
        var displayValue = isValueSet ? value : "VALUE NOT SET";
        return tableName + ',' + cosmicPrimaryKey + "_" + novaPrimaryKey +',' + columnName + ',' + "'" + displayValue + "'\n";
    };
});