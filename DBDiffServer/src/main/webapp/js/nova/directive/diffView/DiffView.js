angular.module('diffApp').directive('diffView', function (FileExportService, $routeParams, TestSuiteResultService) {
    return {
        restrict: 'E',
        templateUrl: 'DiffView.html',
        scope: {
            tables: '=',
            errorMsg: '='
        },
        link: function (scope) {

            scope.getCosmicDisplayValue = function (column) {
                if (column.cosmicValueSet) {
                    return getDisplayValue(column.cosmicValue);
                }
                else {
                    return "VALUE NOT SET";
                }
            };

            scope.getNovaDisplayValue = function (column) {
                if (column.novaValueSet) {
                    return getDisplayValue(column.novaValue);
                }
                else {
                    return "VALUE NOT SET";
                }
            };

            scope.isEmpty = function (value) {
                return value == null || value == undefined || value == "";
            };

            scope.exportReport = function (tables) {
                FileExportService.export(tables);
            };

            function getDisplayValue(value) {
                return value === null ? "NULL" : value;
            };
        }
    };
});